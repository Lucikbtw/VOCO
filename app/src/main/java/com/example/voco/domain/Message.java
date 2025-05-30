package com.example.voco.domain;

public class Message {
    private String id;
    private String chatId;
    private String senderId;
    private String senderName;
    private String text;
    private long timestamp;
    private boolean isRead;
    private MessageType messageType;
    private boolean isSentByCurrentUser;

    public enum MessageType {
        TEXT, IMAGE, FILE, VOICE
    }

    // Конструкторы
    public Message() {}

    public Message(String chatId, String senderId, String senderName, String text, boolean isSentByCurrentUser) {
        this.id = generateId();
        this.chatId = chatId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.text = text;
        this.timestamp = System.currentTimeMillis();
        this.isRead = false;
        this.messageType = MessageType.TEXT;
        this.isSentByCurrentUser = isSentByCurrentUser;
    }

    private String generateId() {
        return String.valueOf(System.currentTimeMillis()) + "_" + (int)(Math.random() * 1000);
    }

    // Геттеры и сеттеры
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getChatId() { return chatId; }
    public void setChatId(String chatId) { this.chatId = chatId; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public MessageType getMessageType() { return messageType; }
    public void setMessageType(MessageType messageType) { this.messageType = messageType; }

    public boolean isSentByCurrentUser() { return isSentByCurrentUser; }
    public void setSentByCurrentUser(boolean sentByCurrentUser) { isSentByCurrentUser = sentByCurrentUser; }
}