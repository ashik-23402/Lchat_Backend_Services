package com.ashik.WhatsApp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageReq {

    private  Integer userId;
    private Integer chatId;
    private String content;
}
