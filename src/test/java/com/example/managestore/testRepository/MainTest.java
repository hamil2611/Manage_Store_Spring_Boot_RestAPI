package com.example.managestore.testRepository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainTest {
    public static void main(String[] args) {

        String str = "2016-03-04 11:30:40";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        System.out.println(dateTime);

    }
}
