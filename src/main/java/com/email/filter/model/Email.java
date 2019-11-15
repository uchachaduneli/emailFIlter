package com.email.filter.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "email", schema = "emailfilter")
public class Email {
    private Integer id;
    private Users user;
    private String from;
    private String to;
    private String subject;
    private Timestamp sendDate;
    private Timestamp receiveDate;
    private String content;
    private String uid;//meilis uniq ID
    private Timestamp insertDate;
    private EmailFolders folder;
    private String senderIp;
    private String attachments;

    public Email(String from, String to, String subject, Timestamp sendDate,
                 Timestamp receiveDate, String content, String uid, Users user, EmailFolders folder, String senderIp, String attachments) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.sendDate = sendDate;
        this.receiveDate = receiveDate;
        this.content = content;
        this.uid = uid;
        this.user = user;
        this.folder = folder;
        this.senderIp = senderIp;
        this.attachments = attachments;
    }

    public Email() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JoinColumn(name = "user_id")
    @OneToOne
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Basic
    @Column(name = "`from`")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Basic
    @Column(name = "`to`")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Basic
    @Column(name = "`subject`")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "send_date")
    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }

    @Basic
    @Column(name = "receive_date", nullable = true)
    public Timestamp getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Timestamp receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "sender_ip")
    public String getSenderIp() {
        return senderIp;
    }

    public void setSenderIp(String senderIp) {
        this.senderIp = senderIp;
    }

    @Basic
    @Column(name = "uid", nullable = true)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "insert_date", updatable = false, insertable = false)
    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    @JoinColumn(name = "folder_id")
    @OneToOne
    public EmailFolders getFolder() {
        return folder;
    }

    public void setFolder(EmailFolders folder) {
        this.folder = folder;
    }

    @Basic
    @Column(name = "attachments")
    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }
}
