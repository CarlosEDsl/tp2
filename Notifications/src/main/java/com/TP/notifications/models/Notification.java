package com.TP.notifications.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Notification {

    public Notification(UUID receiverId, UUID senderId, String type, String redirect) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.type = type;
        this.isRead = false;
        this.redirect = redirect;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private UUID receiverId;

    @Column
    private UUID senderId;

    @Column
    private String type;

    @Column
    private boolean isRead;

    @Column
    private String redirect;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
