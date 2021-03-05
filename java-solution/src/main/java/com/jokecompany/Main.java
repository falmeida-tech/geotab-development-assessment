package com.jokecompany;

import com.jokecompany.exceptions.JokeCompanyUIException;
import com.jokecompany.exceptions.JsonFeedException;
import com.jokecompany.view.JokeCompanyUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws JokeCompanyUIException {
        JokeCompanyUI jokeCompanyUI = new JokeCompanyUI();
        jokeCompanyUI.showAllOptions();
    }

}

