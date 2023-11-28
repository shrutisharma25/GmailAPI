package com.example.demo.services;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GmailAPIService {

    private final Gmail gmail;

    @Autowired
    public GmailAPIService(Gmail gmail) {
        this.gmail = gmail;
    }

    public List<Message> getLast200Emails() throws IOException {
        ListMessagesResponse response = gmail.users().messages().list("me").setMaxResults(200L).execute();
        return response.getMessages();
    }

    public String getSender(String messageId) throws IOException {
        Message message = gmail.users().messages().get("me", messageId).execute();
        return message.getPayload().getHeaders().stream()
                .filter(header -> "From".equals(header.getName()))
                .findFirst()
                .map(com.google.api.services.gmail.model.MessagePartHeader::getValue)
                .orElse("Unknown Sender");
    }

    public String getSubject(String messageId) throws IOException {
        Message message = gmail.users().messages().get("me", messageId).execute();
        return message.getPayload().getHeaders().stream()
                .filter(header -> "Subject".equals(header.getName()))
                .findFirst()
                .map(com.google.api.services.gmail.model.MessagePartHeader::getValue)
                .orElse("Unknown Subject");
    }
}
