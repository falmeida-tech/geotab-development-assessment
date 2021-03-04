package com.jokecompany.model;

public class FullNameDTO {

    private String name , surname;

    public FullNameDTO(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "DTO{" + "name='" + name + '\'' + ", surname='" + surname + '\'' + '}';
    }

}
