package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.GmailAPIInfo;
import com.example.demo.services.GmailAPIService;
import com.google.api.services.gmail.model.Message;

@RestController
@RequestMapping("/emails")
public class GmailAPIController {
	
	private final GmailAPIService gmailService;

    @Autowired
    public GmailAPIController(GmailAPIService gmailService) {
        this.gmailService = gmailService;
    }

    @GetMapping("/last200mails")
    public ResponseEntity<List<GmailAPIInfo>> getLast200Emails() {
        try {
            List<Message> emails = gmailService.getLast200Emails();
            List<GmailAPIInfo> gmailInfoList = emails.stream()
                    .map(this::mapToGmailAPIInfo)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(gmailInfoList);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private GmailAPIInfo mapToGmailAPIInfo(Message message) {
        try {
            String sender = gmailService.getSender(message.getId());
            String subject = gmailService.getSubject(message.getId());

            return new GmailAPIInfo(sender, subject);
        } catch (IOException e) {
            e.printStackTrace();
            return new GmailAPIInfo("Unknown Sender", "Unknown Subject");
        }
    }
}
