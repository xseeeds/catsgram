package ru.yandex.practicum.catsgram.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChatMessage {

    private Integer id;

    private String userFrom;

    private String userTo;

    private LocalDate sendDate;

    private String message;

    private boolean userRead;

}
