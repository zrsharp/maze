package com.zero.maze.dto;

import java.io.Serializable;

public class BaseResultDto<T> implements Serializable {
    private static final long serialVersionUID = -9071835030947014389L;
    
    private boolean success;
    private String message;
    private T data;
    
    public BaseResultDto() {
        
    }

    public BaseResultDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BaseResultDto(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
