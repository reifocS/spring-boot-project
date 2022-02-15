package com.projetspring.projet.exceptions;

public class MovieCreationWithoutActorsException extends Exception{
    public MovieCreationWithoutActorsException(String message) {
        super(message);
    }
}