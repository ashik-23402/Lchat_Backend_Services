package com.ashik.WhatsApp.Entities;

import com.ashik.WhatsApp.Utils.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String chat_name;

    private String chat_image;

    @Column(name="is_group")
    private boolean isGroup;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private  User createBy;
    @ManyToMany
    private Set<User> users = new HashSet<>();
    @OneToMany
    private List<Message> messages = new ArrayList<>();

}
