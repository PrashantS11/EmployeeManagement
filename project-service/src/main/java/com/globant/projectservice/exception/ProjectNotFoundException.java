package com.globant.projectservice.exception;

public class ProjectNotFoundException extends RuntimeException{

   // private String message;

    public ProjectNotFoundException(String message) {
         super(message);
    }
}
