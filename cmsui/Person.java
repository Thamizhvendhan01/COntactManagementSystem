package com.example.cmsui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Person {
    String name;
    List<String> numbers ;

    String description;
    LocalDate addDate;
    LocalDate modifiedDate;

    public Person(String name,List<String> numbers,String description){
        this.name = name;
        this.description = description;
        this.numbers = numbers;
    }
    public Person(String name,String description){
        this.name = name;
        this.description = description;
    }
    public LocalDate getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDate ld) {
        this.addDate = ld;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
    public void addNumber(String s){
        numbers.add(s);
    }
    public void removeNumber(String s){
        numbers.remove(s);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}

