package com.ashik.WhatsApp.Utils;

import com.ashik.WhatsApp.Entities.Chat;
import com.ashik.WhatsApp.Entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String Content;

    private LocalDateTime timestamp;
    @ManyToOne
    private User user;
    @ManyToOne
    private Chat chat;

}
