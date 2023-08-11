package com.example.journalparser;


import com.example.journalparser.code.ExtractAuthorFromFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@SpringBootApplication
public class JournalParserApplication {



    public static void main(String[] args) {
        SpringApplication.run(JournalParserApplication.class, args);
    }



}
