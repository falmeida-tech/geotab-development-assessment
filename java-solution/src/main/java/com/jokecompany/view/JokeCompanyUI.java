package com.jokecompany.view;

import com.jokecompany.exceptions.JokeCompanyUIException;
import com.jokecompany.exceptions.JsonFeedException;
import com.jokecompany.model.FullNameDTO;
import com.jokecompany.services.JsonFeed;
import com.jokecompany.utilities.ConsolePrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class JokeCompanyUI {

    private static String[] results = new String[50];
    private static char key = 'e';
    private static Hashtable<String, String> names = new Hashtable<>();
    private static ConsolePrinter printer = new ConsolePrinter();

    private JsonFeed jsonFeed = new JsonFeed();

    public JokeCompanyUI() {

    }

    public void readUserInput(BufferedReader reader) throws JokeCompanyUIException {
        try {
            getEnteredKey(reader.readLine());
        }catch(IOException e){
            throw new JokeCompanyUIException(e.getMessage());
        }
    }

    public void showMainMenu(BufferedReader reader) throws JokeCompanyUIException {
        printer.Value("Press c to get categories").toString();
        printer.Value("Press r to get random jokes").toString();
        printer.Value("Press x to exit").toString();
        readUserInput(reader);
    }

    public void showRandomNameMenu(BufferedReader reader) throws JokeCompanyUIException {
        printer.Value("Want to use a random name? y/n").toString();
        readUserInput(reader);
    }

    public void showNames(BufferedReader reader) throws JsonFeedException, JokeCompanyUIException {
        FullNameDTO fullNameDTO = jsonFeed.getNames();
        printer.Value(fullNameDTO.toString()).toString();
        printer.Value("Want to specify a category? y/n").toString();
        readUserInput(reader);
    }

    public void showRandomJokes(BufferedReader reader) throws IOException, JsonFeedException {
        printer.Value("How many jokes do you want? (1-9)").toString();
        int number = Integer.parseInt(reader.readLine());
        jsonFeed.setResults(number);
        results = jsonFeed.getRandomJokes(null);
        PrintResults();
    }

    public void showRandomJokesByCategory(BufferedReader reader) throws IOException, JsonFeedException {
        printer.Value("How many jokes do you want? (1-9)").toString();
        int number = Integer.parseInt(reader.readLine());
        printer.Value("Enter a category;").toString();
        String category = reader.readLine();
        results = jsonFeed.getRandomJokes(category);
        PrintResults();
    }

    public void showCategories() throws JsonFeedException {
        results = jsonFeed.getCategories();
        PrintResults();
    }

    public void showAllOptions() throws JokeCompanyUIException {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            printer.Value("Press ? to get instructions.").toString();
            String input = reader.readLine();
            if (input.equals("?")) {
                while (key == 'e' || key != 'x') {
                    showMainMenu(reader);
                    if (key == 'c') {
                        showCategories();
                    }
                    if (key == 'r') {
                        showRandomNameMenu(reader);
                        if (key == 'y') {
                            showNames(reader);
                        }
                        if (key == 'y') {
                            showRandomJokesByCategory(reader);
                        }else{
                            showRandomJokes(reader);
                        }
                    }
                    names = null;
                }
            }

        } catch (JsonFeedException | IOException e) {
            throw new JokeCompanyUIException(e.getMessage());
        }

    }

    private static void getEnteredKey(String k) throws JokeCompanyUIException {

        char keySelected = k.substring(0,1).charAt(0);

        switch (keySelected) {
            case 'c':
                key = 'c';
                break;
            case 'r':
                key = 'r';
                break;
            case 'y':
                key = 'y';
                break;
            default:
                key = keySelected;
                break;
        }

    }

    private static void PrintResults() {
        printer.Value("[" + String.join(",", results) + "]").toString();
    }

}
