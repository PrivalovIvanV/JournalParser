package com.example.journalparser.code.exceptions;

public class ChatGptResponseException extends RuntimeException{

    public ChatGptResponseException(String message) {
        super(message);
    }


}
