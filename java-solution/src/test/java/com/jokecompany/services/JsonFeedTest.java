package com.jokecompany.services;

import com.jokecompany.exceptions.JsonFeedException;
import com.jokecompany.model.FullNameDTO;
import static org.junit.Assert.*;

import org.junit.Test;

public class JsonFeedTest {

    @Test
    public void testGetNames_condition_Name_And_Surname_Not_Empty() throws JsonFeedException {
        JsonFeed jsonFeed = new JsonFeed();
        FullNameDTO fullNameDTO = jsonFeed.getNames();
        assertFalse(fullNameDTO.getName().isEmpty());
        assertFalse(fullNameDTO.getSurname().isEmpty());
    }

}