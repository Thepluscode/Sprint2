package gp_booking_system;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


import com.mysql.cj.protocol.Message;

public class UserMessage implements  Message {
    public static Object RecipientType;
    private int messageId;
    private String senderId;
    private String receiverId;
    private String messageContent;
    private LocalDateTime timestamp;

    public UserMessage(int messageId, String senderId, String receiverId, String senderRole, String receiverRole, String messageContent, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public UserMessage(int messageId2, String senderId2, String messageContent2, Timestamp timestamp2) {
    }

    public UserMessage(int messageId2, String senderId2, String patientId, String messageContent2,
            Timestamp timestamp2) {
    }

    // Getters and setters for message fields
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // toString method for debugging or displaying messages
    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", messageContent='" + messageContent + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

   // Implement methods from Message interface
@Override
public byte[] getByteBuffer() {
    try {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this); // Serialize the UserMessage object
        oos.flush();
        return bos.toByteArray();
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}

@Override
public int getPosition() {
    // This method may not be applicable in this context, return 0 by default
    return 0;
}

}

