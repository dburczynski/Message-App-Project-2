package com.jee.messagingapp2.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Role {

    @Transient
    public static final String SEQUENCE_NAME = "roles_sequence";

    @Id
    private long id;

    private String name;

    public Role() {}

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
