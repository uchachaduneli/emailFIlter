package com.email.filter.service;


import com.email.filter.dao.FilterDAO;
import com.email.filter.dao.MailDAO;
import com.email.filter.dao.UserDAO;
import com.email.filter.dto.*;
import com.email.filter.model.Email;
import com.email.filter.model.EmailFolders;
import com.email.filter.model.Filter;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email credentials Username: emailfilter19@gmail.com  pass: 123!@#asdASD
 */
@Service
public class MailService {
    Logger logger = Logger.getLogger(MailService.class);

    @Autowired
    private MailDAO mailDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private FilterDAO filterDAO;

    public static void main(String[] args) throws Exception {
        MailService ms = new MailService();
        ms.loadEmails();
//        ms.read();
    }

    public List<FilterDTO> getFilters() {
        return FilterDTO.parseToList(filterDAO.getAll(Filter.class));
    }

    public List<EmailDTO> getEmails(int start, int limit, MailRequest srchRequest) throws ParseException {
        return EmailDTO.parseToList(mailDAO.getEMails(start, limit, srchRequest));
    }

    public List<EmailFolderDTO> getEmailFolders() {
        return EmailFolderDTO.parseToList(mailDAO.getAll(EmailFolders.class));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void delete(int id) {
        Email email = (Email) mailDAO.find(Email.class, id);
        if (email != null) {
            mailDAO.delete(email);
        }
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

    @Transactional(rollbackFor = Throwable.class)
    public void loadEmails() throws Exception {
        logger.debug("Syncronizer method started" + getActiveUsersList().size());
        List<FilterDTO> filters = getFilters();
        for (Users user : getActiveUsersList()) {
            Properties props2 = System.getProperties();
            props2.put("mail.smtp.host", "smtp.gmail.com");
            props2.put("mail.smtp.socketFactory.port", "465");
            props2.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props2.put("mail.smtp.port", "465");
            props2.put("mail.smtp.auth", "true");
            Session emailSession = Session.getDefaultInstance(props2, null);
            Store store = emailSession.getStore("imaps");
            store.connect("smtp.gmail.com", user.getEmail(), user.getEmailPassword());

            Folder inboxFolder = store.getFolder("INBOX");//get inbox
            Folder spamFolder = store.getFolder("MySpam");
            inboxFolder.open(Folder.READ_WRITE);//open folder only to read
            Message inboxMails[] = inboxFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            List<Email> emails = new ArrayList<>();
            for (int i = 0; i < inboxMails.length; i++) {
                boolean moveToMySpam = false;
                Message message = inboxMails[i];
                Address[] fromAddress = message.getFrom();
                String from = ((InternetAddress) fromAddress[0]).getAddress();
                String subject = message.getSubject();
                Date sentDate = message.getSentDate();
                Date receiveDate = message.getReceivedDate();
                String contentType = message.getContentType();
                String senderIp = "";
                String messageContent = "";
                Object content = message.getContent();
                if (content != null) {
                    messageContent = (message.getContent() instanceof MimeMultipart ?
                            getTextFromMimeMultipart((MimeMultipart) message.getContent()) : content.toString());
                }
                for (FilterDTO filter : filters) {
                    if (filter.getType().getId() == FilterTypeDTO.IP_FILTER) {// filtering by sender ip
                        Enumeration headers = message.getAllHeaders();
                        while (headers.hasMoreElements()) {
                            Header h = (Header) headers.nextElement();
                            if (h.getName().equals("Received") && h.getValue().contains("from")) {
                                Pattern p = Pattern.compile("\\[(.*?)\\]");
                                Matcher m = p.matcher(h.getValue());
                                while (m.find()) {
                                    senderIp = m.group(1);
                                    if (m.group(1).trim().equals(filter.getDesc().trim())) { // Sender IP equals filter value
                                        moveToMySpam = true;
                                    }
                                }
                            }
                        }
                    } else {
                        if (messageContent.toUpperCase().contains(filter.getDesc().toUpperCase()) ||
                                subject.toUpperCase().contains(filter.getDesc().toUpperCase())) {
                            moveToMySpam = true;
                        }
                    }
                }
                EmailFolders emFolder = (EmailFolders) mailDAO.find(EmailFolders.class, moveToMySpam ? EmailFolderDTO.SPAM : EmailFolderDTO.INBOX);
                mailDAO.create(new Email(from, user.getEmail(), subject, new Timestamp(sentDate.getTime()),
                        new Timestamp(receiveDate.getTime()), messageContent, "", user,
                        emFolder, senderIp));

                message.setFlag(Flags.Flag.SEEN, true);//set Seen flag or move to correct folder and delete from incorrect one here
                if (moveToMySpam) {
                    //                moving messages to proper folder
                    List<Message> tempList = new ArrayList<>();
                    tempList.add(message);
                    Message[] tempMessageArray = tempList.toArray(new Message[tempList.size()]);
                    inboxFolder.copyMessages(tempMessageArray, spamFolder);

                    //				print out details of each message
                    System.out.println("\t From: " + from);
                    System.out.println("\t Subject: " + subject);
                    System.out.println("\t Sent Date: " + sentDate);
                    System.out.println("\t Receive Date: " + receiveDate);
                    System.out.println("\t Message: " +
                            (message.getContent() instanceof MimeMultipart ?
                                    getTextFromMimeMultipart((MimeMultipart) message.getContent()) : messageContent)
                            + " \n ***********   NEXT ONE    ********** \n");
                }
            }
            inboxFolder.close(true);
            store.close();
        }
    }

    private List<Users> getActiveUsersList() {
        List<Users> tmp = userDAO.getAll(Users.class);
        List<Users> users = new ArrayList<>();
        for (Users user : tmp) {
            if (user.getEmail() != null && user.getEmailPassword() != null && user.getDeleted() != UsersDTO.DELETED) {
                users.add(user);
            }
        }
        return users;
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