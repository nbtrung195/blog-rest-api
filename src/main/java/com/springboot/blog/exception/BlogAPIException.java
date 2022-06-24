package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogAPIException extends RuntimeException  {
    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIException() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
