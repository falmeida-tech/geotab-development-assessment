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

public class JsonFeed {

    static String url = "";

    public JsonFeed() {

    }

    public JsonFeed(String endpoint, int results) {

        url = endpoint;

    }

    public static String[] getRandomJokes(String firstname, String lastname, String category) throws URISyntaxException, IOException, InterruptedException {
        String baseUrl = "https://api.chucknorris.io/jokes/random";

        if (StringUtils.isNoneEmpty(category))
            baseUrl += category;

        HttpClient client = HttpClient.newHttpClient();
        URI uri = new URI(baseUrl);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String joke = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        if (firstname != null && lastname != null) {
            int index = joke.indexOf("Chuck Norris");
            String firstPart = joke.substring(0, index);
            String secondPart = joke.substring(index + "Chuck Norris".length());
            joke = firstPart + " " + firstname + " " + lastname + secondPart;
        }
        Gson jsonobject = new GsonBuilder().disableHtmlEscaping().create();
        return new String[] {jsonobject.toJson(joke)};
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
            String names = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            fullNameDTO = gson.fromJson(names, FullNameDTO.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new JsonFeedException(e.getMessage());
        }
        return fullNameDTO;
    }

    public static String[] getCategories() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = new URI(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String responsebody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return new String[] {new Gson().toJson(responsebody)};
    }

}
