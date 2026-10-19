package com.mycompany.quickchatapp;
// Part 2 Message class
// cell format check
// message length limit
// message ID generation
import java.util.Random;

/**
 * Encapsulates message payload, hash generation, and array management operations.
 */
public class Message {
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String messageStatus; // Sent, Disregarded, Stored

    public Message() {}

    public Message(int messageNumber, String recipient, String messageText, String actionChoice) {
        this.numMessagesSent = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.messageStatus = SentMessage(actionChoice);
    }

    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    public String checkRecipientCell(String recipient) {
        Login loginValidator = new Login();
        if (loginValidator.checkCellPhoneNumber(recipient)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageText == null || messageText.trim().isEmpty()) {
            return "00:0:UNKNOWN";
        }
        String idPrefix = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-0]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-0]", "");
        
        return (idPrefix + ":" + numMessagesSent + ":" + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage(String choice) {
        switch (choice) {
            case "1":
                this.messageStatus = "Sent";
                return "Message successfully sent.";
            case "2":
                this.messageStatus = "Disregarded";
                return "Press 0 to delete the message.";
            case "3":
                this.messageStatus = "Stored";
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public String printMessages() {
        return "Message ID: " + messageID +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + messageText +
               "\nStatus: " + messageStatus;
    }

    public String validateMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = text.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    /*
     * Custom JSON Storage Simulator Method
     * Code Attribution:
     * JSON parsing concept adapted from Org.JSON Documentation (2024 cited in The IIE, 2026).
     */
    public String storeMessageJSON() {
        return "{\n" +
               "  \"messageID\": \"" + messageID + "\",\n" +
               "  \"hash\": \"" + messageHash + "\",\n" +
               "  \"recipient\": \"" + recipient + "\",\n" +
               "  \"message\": \"" + messageText + "\"\n" +
               "}";
    }

    // Getters and Setters
    public String getMessageID() { return messageID; }
    public void setMessageID(String messageID) { this.messageID = messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getMessageStatus() { return messageStatus; }
    public int getNumMessagesSent() { return numMessagesSent; }
}