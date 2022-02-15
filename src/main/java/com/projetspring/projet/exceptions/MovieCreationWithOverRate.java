package com.projetspring.projet.exceptions;

public class MovieCreationWithOverRate extends Exception{
    public MovieCreationWithOverRate(String message) {
        super(message);
    }
}