package com.yeonny.back.service;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class ParthingDataService {
    //https://jeong-pro.tistory.com/212
    
    String filePath = "member.csv";
    List<List<String>> persons = new ArrayList<>();
    String line;
    
    public List<List<String>> readValue(){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            while((line = br.readLine())!= null){
                List<String> person = new ArrayList<>();
                String[] values = line.split(",");
                for(String value : values){
                    person.add(value.trim());
                }
                persons.add(person);
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
        return persons;
    }
}
