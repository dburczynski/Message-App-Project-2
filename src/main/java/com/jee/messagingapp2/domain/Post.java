package com.jee.messagingapp2.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.sql.Date;
import java.util.Calendar;

@Document
public class Post {

    @Transient
    public static final String SEQUENCE_NAME = "posts_sequence";

    @Id
    private long id;

    private String message;

    private String createDate;

    private String user;

    public Post() {
        this.createDate = new Date(Calendar.getInstance().getTimeInMillis()).toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {return user;}

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
