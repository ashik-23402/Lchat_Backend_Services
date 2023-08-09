package com.ashik.WhatsApp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatRequest {

    private List<Integer> usersId;
    private String chat_name;
    private String chat_image;



}
