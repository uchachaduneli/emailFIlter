package com.email.filter.service;


import com.email.filter.dao.MailDAO;
import com.email.filter.dao.UserDAO;
import com.email.filter.dto.EmailDTO;
import com.email.filter.dto.EmailFolderDTO;
import com.email.filter.dto.UsersDTO;
import com.email.filter.model.Email;
import com.email.filter.model.EmailFolders;
import com.email.filter.model.Users;
import com.email.filter.request.MailRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author
 */
@Service
public class MailService {
    Logger logger = Logger.getLogger(MailService.class);

    @Autowired
    private MailDAO mailDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UsersService userService;

    public static void main(String[] args) throws Exception {
        MailService ms = new MailService();
        Users u = new Users();
        u.setEmail("emailfilter19@gmail.com");
        u.setEmailPassword("123!@#asdASD");

        EmailFolders f = new EmailFolders();
        f.setName("INBOX");

        ms.loadEmails(u, f);
    }

    public List<EmailDTO> getEmails(int start, int limit, MailRequest srchRequest) throws ParseException {
        return EmailDTO.parseToList(mailDAO.getEMails(start, limit, srchRequest));
    }

    public List<EmailFolderDTO> getEmailFolders() throws ParseException {
        return EmailFolderDTO.parseToList(mailDAO.getAll(EmailFolders.class));
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }

    public void loadEmails(Users user, EmailFolders emailFolder) {
        try {

            Properties props2 = System.getProperties();
            props2.put("mail.pop3.host", "pop.gmail.com");
            props2.put("mail.pop3.port", "995");
            props2.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(props2);

            Store store = emailSession.getStore("pop3s");
            Session session2 = Session.getInstance(props2, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user.getEmail(), user.getEmailPassword());
                }
            });

            store.connect("pop.gmail.com", user.getEmail(), user.getEmailPassword());

            Folder folder = store.getFolder("INBOX");//get inbox
            folder.open(Folder.READ_ONLY);//open folder only to read
            Message inboxMails[] = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            List<Email> emails = new ArrayList<>();
            for (int i = 0; i < inboxMails.length; i++) {
                Message message = inboxMails[i];
                Address[] fromAddress = message.getFrom();
                String from = ((InternetAddress) fromAddress[0]).getAddress();
                String subject = message.getSubject();
                Date sentDate = message.getSentDate();
                Date receiveDate = message.getReceivedDate();
                String contentType = message.getContentType();
                String messageContent = "";

                Object content = message.getContent();
                if (content != null) {
                    messageContent = content.toString();
                }

                // mailDAO.create(new Email(from, user.getEmail(), subject, new Timestamp(sentDate.getTime()),
                //       new Timestamp(receiveDate.getTime()), messageContent, "", user, emailFolder));

                message.setFlag(Flags.Flag.SEEN, true);//set Seen flag or move to correct folder and delete from incorrect one here
//				print out details of each message

                System.out.println("\t From: " + from);
                System.out.println("\t Subject: " + subject);
                System.out.println("\t Sent Date: " + sentDate);
                System.out.println("\t Receive Date: " + receiveDate);
                System.out.println("\t Message: " +
                        (message.getContent() instanceof MimeMultipart ?
                                getTextFromMimeMultipart((MimeMultipart) message.getContent()) : messageContent)
                        + " \n ***********   NEXT ONE    ********** \n");

//                moving messages to proper folder
//                List<Message> tempList = new ArrayList<>();
//                tempList.add(myImapMsg);
//                Message[] tempMessageArray = tempList.toArray(new Message[tempList.size()]);
//                fromFolder.copyMessages(tempMessageArray, destFolder);
            }
            folder.close(true);
            store.close();
        } catch (javax.mail.MessagingException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public void processUnreadEmails() {
        List<UsersDTO> users = userService.getUsers();
        for (UsersDTO user : users) {
            if (user.getEmail() != null && user.getEmailPassword() != null && user.getDeleted() != UsersDTO.DELETED) {
                // loadEmails(user.getUserId(), user.getEmail(), user.getEmailPassword());
            }
        }
    }

    public void sendNotifUsingGmail(String to, String emailSubject, String text) throws MessagingException {
        logger.debug("Started Sending Mail");
        try {

            Properties emailProperties;
            Session mailSession;
            MimeMessage emailMessage;
            emailProperties = System.getProperties();
            emailProperties.put("mail.smtp.port", "587");
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");
            emailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            mailSession = Session.getDefaultInstance(emailProperties, null);
            emailMessage = new MimeMessage(mailSession);

            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            Multipart mmp = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);

            emailMessage.setSubject(emailSubject);
            emailMessage.setContent(mmp);

            Transport transport = mailSession.getTransport("smtp");

            transport.connect("smtp.gmail.com", "emailfilter19@gmail.com", "123!@#asdASD");
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            logger.error("Error While Sending Password Restoration", ex);
            throw ex;
        }
    }
}