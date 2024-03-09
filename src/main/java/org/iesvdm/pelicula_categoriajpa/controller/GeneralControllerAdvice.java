package org.iesvdm.pelicula_categoriajpa.controller;

import org.iesvdm.pelicula_categoriajpa.exception.EntityNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeneralControllerAdvice {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus
    public String entityNotFoundHandler(EntityNotFoundException entityNotFoundException) {
        return entityNotFoundException.getMessage();
    }
}