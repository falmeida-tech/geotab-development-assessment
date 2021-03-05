package com.jokecompany.services;

import com.jokecompany.exceptions.JsonFeedException;
import com.jokecompany.model.FullNameDTO;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashMap;

public class JsonFeedTest {

    @Test
    public void testGetNames_condition_Name_And_Surname_Not_Empty() throws JsonFeedException {
        JsonFeed jsonFeed = new JsonFeed();
        FullNameDTO fullNameDTO = jsonFeed.getNames();
        assertFalse(fullNameDTO.getName().isEmpty());
        assertFalse(fullNameDTO.getSurname().isEmpty());
    }

    @Test
    public void testGetCategories_condition_fetch_all_categories() throws JsonFeedException {
        JsonFeed jsonFeed = new JsonFeed();
        String[] categories = jsonFeed.getCategories();
        String[] expectedCategories = new String[]{"animal", "career", "celebrity", "dev", "explicit", "fashion", "food", "history","money", "movie", "music", "political", "religion", "science", "sport", "travel"};
        assertArrayEquals(expectedCategories,categories);
    }

    @Test
    public void testGetRandomJokes_condition_provide_existing_category_one_joke() throws JsonFeedException {
        HashMap<String,String> names = new HashMap<>();
        names.put("Firstname","Lastname");
        JsonFeed jsonFeed = new JsonFeed();
        jsonFeed.setNames(names);
        jsonFeed.setResults(1);
        String[] randomJokes = jsonFeed.getRandomJokes("food");
        assertEquals(randomJokes.length,1);
    }

    @Test
    public void testGetRandomJokes_condition_non_category_three_jokes() throws JsonFeedException {
        HashMap<String,String> names = new HashMap<>();
        names.put("Firstname","Lastname");
        JsonFeed jsonFeed = new JsonFeed();
        jsonFeed.setNames(names);
        jsonFeed.setResults(3);
        String[] randomJokes = jsonFeed.getRandomJokes(null);
        assertEquals(randomJokes.length,3);
    }

    @Test
    public void testGetRandomJokes_condition_provide_non_category_nine_jokes() throws JsonFeedException {
        HashMap<String,String> names = new HashMap<>();
        names.put("Firstname","Lastname");
        JsonFeed jsonFeed = new JsonFeed();
        jsonFeed.setNames(names);
        jsonFeed.setResults(9);
        String[] randomJokes = jsonFeed.getRandomJokes(null);
        assertEquals(randomJokes.length,9);
    }

    @Test(expected = JsonFeedException.class)
    public void testGetRandomJokes_condition_non_valid_category_throw_exception() throws JsonFeedException {
        HashMap<String,String> names = new HashMap<>();
        names.put("Firstname","Lastname");
        JsonFeed jsonFeed = new JsonFeed();
        jsonFeed.setResults(1);
        jsonFeed.setNames(names);
        String[] randomJokes = jsonFeed.getRandomJokes("invalid_category");
        assertEquals(randomJokes.length, 1);
    }

}