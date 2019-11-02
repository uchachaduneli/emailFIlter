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

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
//        sendNotifUsingGmail(1);
//        String mailFrom = new String("miminotest@gmail.com");
//        String mailTo = new String("miminotest@gmail.com");

        String senderPassword = new String("1qaz@WSX1qaz");
        String senderUserName = new String("miminotest@yandex.ru");

//        String senderPassword = new String("Testirebah1");
//        String senderUserName = new String("administrator@hotelhub.ge");

//        MailService newGmailClient = new MailService();

//        newGmailClient.sendGmail(mailFrom, mailTo, mailSubject, mailText);

//        newGmailClient.readGmail();
//        newGmailClient.downloadEmailAttachments(senderUserName, senderPassword);
// ************************************************************

//        List<String> addresats = new ArrayList<>();
////        addresats.add("uchachaduneli@gmail.com");
//        addresats.add("david.kaliashvili@gmail.com");
//
//        List<String> attachments = new ArrayList<>();
//        attachments.add("C:\\Users\\ME\\IdeaProjects\\miminoTravel\\src\\main\\resources\\0012.pdf");
//
//        newGmailClient.sendEmail(senderUserName, senderPassword,
//                addresats,
//                null, attachments, "TEST MAIL SUBJECT", "ეს არის სატესტო მეილის ტექსტი");
    }

    public List<EmailDTO> getEmails(int start, int limit, MailRequest srchRequest) throws ParseException {
        return EmailDTO.parseToList(mailDAO.getEMails(start, limit, srchRequest));
    }

    public List<EmailFolderDTO> getEmailFolders() throws ParseException {
        return EmailFolderDTO.parseToList(mailDAO.getAll(EmailFolders.class));
    }

    @Transactional(rollbackFor = Throwable.class)
    public Email save(MailRequest request) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Email obj = new Email();
        obj.setFrom(request.getFrom());
        obj.setTo(request.getTo());
        obj.setSubject(request.getSubject());
        obj.setContent(request.getContent());
        obj.setAttachments(request.getAttachments());
        obj.setSendDate(new Timestamp(sdf.parse(request.getSendDate()).getTime()));
        obj.setReceiveDate(new Timestamp(sdf.parse(request.getReceiveDate()).getTime()));
        obj.setUser((Users) mailDAO.find(Users.class, request.getUserId()));
//
        if (request.getId() != null) {
            obj.setId(request.getId());
            obj = (Email) mailDAO.update(obj);
        } else {
            obj = (Email) mailDAO.create(obj);
        }

        return obj;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws MessagingException, IOException {
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

    private MimeBodyPart createFileAttachment(String filepath)
            throws MessagingException {
        MimeBodyPart mbp = new MimeBodyPart();

        FileDataSource fds = new FileDataSource(filepath);
        mbp.setDataHandler(new DataHandler(fds));
        mbp.setFileName(fds.getName());
        return mbp;
    }

    public void downloadEmailWithAttachments(Integer userId, String userName, String password) {
        try {

            Users user = (Users) mailDAO.find(Users.class, userId);
            EmailFolders emailFolder = (EmailFolders) mailDAO.find(EmailFolders.class, 1);//inbox -ია 1 აიდიზე

            Properties props2 = System.getProperties();
            props2.setProperty("mail.store.protocol", "imaps");
            props2.put("mail.debug", "false");
            props2.put("mail.store.protocol", "imaps");
            props2.put("mail.imap.ssl.enable", "true");
            props2.put("mail.imap.port", "993");

            Session session2 = Session.getDefaultInstance(props2, null);


            Store store = session2.getStore("imaps");

            store.connect("imap.yandex.ru", userName, password);

//        Folder[] f = store.getDefaultFolder().list();
//        for(Folder fd:f)
//            System.out.println(">> "+fd.getName()); System.exit(0);

            Folder folder = store.getFolder("INBOX");//get inbox
            UIDFolder uf = (UIDFolder) folder;

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

                // store attachment file name, separated by comma
                String attachFiles = "";

                if (contentType.contains("multipart")) {
                    // content may contain attachments
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // this part is attachment
                            String fileName = part.getFileName();
                            attachFiles += fileName + " ";
                            part.saveFile("C:\\Users\\ME\\Desktop\\attachments" + File.separator + uf.getUID(message) + "_" + fileName);
//                            part.saveFile("C:\\Users\\home\\Desktop\\attachments" + File.separator + uf.getUID(message) + "_" + fileName);
//                            part.saveFile("/usr/binaries/tomcat9/webapps/ROOT/attachments" + File.separator + uf.getUID(message) + "_" + fileName);
                        } else {
                            // this part may be the message content
                            if (inboxMails[i].isMimeType("text/plain")) {
                                messageContent = inboxMails[i].getContent().toString();
                            } else if (inboxMails[i].isMimeType("multipart/*")) {
                                MimeMultipart mimeMultipart = (MimeMultipart) inboxMails[i].getContent();
                                messageContent = getTextFromMimeMultipart(mimeMultipart);
                            }
//                            messageContent = part.getContent().toString();
                        }
                    }

                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }

                mailDAO.create(new Email(from, user.getEmail(), subject, new Timestamp(sentDate.getTime()),
                        new Timestamp(receiveDate.getTime()), messageContent, attachFiles, uf.getUID(message) + "", user, emailFolder));
//                message.setFlag(Flags.Flag.SEEN, true);//თუ აქამდე მოვიდა და ბაზაშიც დასეივდა SEEN დაუსვას მეილზე
//				print out details of each message
                System.out.println("Message ID" + uf.getUID(message) + ":");
                System.out.println("\t From: " + from);
                System.out.println("\t Subject: " + subject);
                System.out.println("\t Sent Date: " + sentDate);
                System.out.println("\t Receive Date: " + receiveDate);
                System.out.println("\t Message: " + messageContent);
                System.out.println("\t Attachments: " + attachFiles);
            }

            // disconnect
            folder.close(false);
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
                downloadEmailWithAttachments(user.getUserId(), user.getEmail(), user.getEmailPassword());
            }
        }
    }

    private String[] getEmailsByStageGroupId(Integer stageId) {
        logger.debug("Retrieving Users Emails For This Stage Types");
        List<String> mails = new ArrayList<String>();
        for (Users u : userDAO.getUsersByTypeId(stageId)) {
            mails.add(u.getEmail());
        }
        logger.debug("Users Emails For This Stage Types Are Loaded");
        return mails.stream().toArray(String[]::new);
    }


    class EmailAuthenticator extends javax.mail.Authenticator {
        private String login;
        private String password;

        public EmailAuthenticator(final String login, final String password) {
            this.login = login;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(login, password);
        }
    }
}