package com.example.cmsui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHandler {
    Map<String,Person> nameToDetails = new HashMap<>();
    Map<String,Person> numberToDetails = new HashMap<>();
    public boolean isNumberExist(String number){
        return numberToDetails.containsKey(number);
    }
    public Person getDetailsByNumber(String number){
        if(numberToDetails.containsKey(number)) return numberToDetails.get(number);
        return new Person("",new ArrayList<>(),"");
    }
    public void saveNumber(String name,String number,String desc){
        List<String> numbers = new ArrayList<>();
        numbers.add(number);
        Person person = new Person(name,numbers,desc);
        person.setAddDate(LocalDate.now());
        person.setModifiedDate(LocalDate.now());
        nameToDetails.put(name,person);
        numberToDetails.put(number,person);
        ShowPane.olp.add(person);
    }

}
