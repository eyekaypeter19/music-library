package com.innovia.ai.music.common.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private String message;

    public NotFoundException(String message){
        super(message);
        this.message = message;
    }

    public NotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.message = errorMessage;
    }
}
