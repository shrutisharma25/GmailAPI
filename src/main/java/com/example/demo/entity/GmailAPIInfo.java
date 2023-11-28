package com.example.demo.entity;

import org.springframework.stereotype.Component;

@Component
public class GmailAPIInfo {
    private String sender;
    private String subject;

    public GmailAPIInfo(String sender, String subject) {
        this.sender = sender;
        this.subject = subject;
    }

    // Getters and setters

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

