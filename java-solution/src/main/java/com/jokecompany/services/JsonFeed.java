package com.jokecompany.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jokecompany.exceptions.JsonFeedException;
import com.jokecompany.model.FullNameDTO;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class JsonFeed {

    private HashMap<String, String> names = new HashMap<>();

    private int results;

    public JsonFeed(){

    }

    public void setNames(HashMap<String, String> names) {
        this.names = names;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public String[] getRandomJokes(String category) throws JsonFeedException {

        var var1 = names.entrySet().iterator().next();

        final String firstName = var1.getKey();

        final String lastName = var1.getValue();

        String baseUrl = "https://api.chucknorris.io/jokes/random";

        String[] randomJokes = new String[results];

        Gson jsonObject = new GsonBuilder().disableHtmlEscaping().create();

        if (StringUtils.isNoneEmpty(category))
            baseUrl += "?category=" + category;

        HttpClient client = HttpClient.newHttpClient();

        try{

            URI uri = new URI(baseUrl);

            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

            for(int i = 0; i < results; i++){

                String joke = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

                if(joke.contains("404")){
                    throw new JsonFeedException("HTTP Response Code 404 - Resource does not exist");
                }

                if (StringUtils.isNoneEmpty(firstName) && StringUtils.isNoneEmpty(lastName)) {
                    int index = joke.indexOf("Chuck Norris");
                    String firstPart = joke.substring(0, index);
                    String secondPart = joke.substring(index + "Chuck Norris".length());
                    joke = firstPart + " " + firstName + " " + lastName + secondPart;
                }

                randomJokes[i] = jsonObject.toJson(joke);

            }

        }catch(URISyntaxException | IOException | InterruptedException e){
            throw new JsonFeedException(e.getMessage());
        }

        return randomJokes;
    }

    /**
     *
     * @return
     */
    public FullNameDTO getNames() throws JsonFeedException {
        Gson gson = new Gson();
        FullNameDTO fullNameDTO = null;
        final String baseUrl = "https://www.names.privserv.com/api/";
        try{
            URI uri = new URI(baseUrl);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            String namesResponse = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            fullNameDTO = gson.fromJson(namesResponse, FullNameDTO.class);
            names.put(fullNameDTO.getName(), fullNameDTO.getSurname());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new JsonFeedException(e.getMessage());
        }
        return fullNameDTO;
    }

    /**
     *
     * @return
     * @throws JsonFeedException
     */
    public String[] getCategories() throws JsonFeedException {
        Gson gson = new GsonBuilder().create();
        final String baseUrl = "https://api.chucknorris.io/jokes/categories";
        String[] categories = null;
        try{
            HttpClient client = HttpClient.newHttpClient();
            URI uri = new URI(baseUrl);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            String responsebody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            categories = gson.fromJson(responsebody, String[].class);
        }catch (InterruptedException | URISyntaxException | IOException e) {
            throw new JsonFeedException(e.getMessage());
        }
        return categories;
    }

}
