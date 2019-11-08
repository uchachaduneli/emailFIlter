package com.email.filter.dto;

import com.email.filter.misc.JsonDateTimeSerializeSupport;
import com.email.filter.model.Email;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailDTO {

    private Integer id;
    private UsersDTO user;
    private Integer userId;
    private String from;
    private String reply;
    private String to;
    private String cc;
    private String subject;
    @JsonSerialize(using = JsonDateTimeSerializeSupport.class)
    private Date sendDate;
    @JsonSerialize(using = JsonDateTimeSerializeSupport.class)
    private Date receiveDate;
    private String content;
    @JsonSerialize(using = JsonDateTimeSerializeSupport.class)
    private Date insertDate;
    private EmailFolderDTO folder;
    private Integer folder_id;

    public static EmailDTO parse(Email record) {
        EmailDTO dto = new EmailDTO();
        dto.setId(record.getId());
        dto.setUser(UsersDTO.parse(record.getUser()));
        dto.setFrom(record.getFrom());
        dto.setTo(record.getTo());
        dto.setSubject(record.getSubject());
        dto.setSendDate(record.getSendDate());
        dto.setReceiveDate(record.getReceiveDate());
        dto.setContent(record.getContent());
        dto.setInsertDate(record.getInsertDate());
        if (record.getFolder() != null) {
            dto.setFolder(EmailFolderDTO.parse(record.getFolder()));
            dto.setFolder_id(record.getFolder().getId());
        }
        return dto;
    }

    public static List<EmailDTO> parseToList(List<Email> records) {
        ArrayList<EmailDTO> list = new ArrayList<EmailDTO>();
        for (Email record : records) {
            list.add(EmailDTO.parse(record));
        }
        return list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsersDTO getUser() {
        return user;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public EmailFolderDTO getFolder() {
        return folder;
    }

    public void setFolder(EmailFolderDTO folder) {
        this.folder = folder;
    }

    public Integer getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(Integer folder_id) {
        this.folder_id = folder_id;
    }
}
