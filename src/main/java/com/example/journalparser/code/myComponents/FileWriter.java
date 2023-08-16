package com.example.journalparser.code.myComponents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
@Slf4j
public class FileWriter {

    public void println(String value, File outputFile) throws IOException {
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        Files.write(outputFile.toPath(), value.getBytes(), StandardOpenOption.APPEND);

    }

    public void write(String value, File outputFile){
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
                Files.write(outputFile.toPath(), value.getBytes(), StandardOpenOption.WRITE);
            } catch (IOException e) {
                log.error("Не удалось перезаписать файл {}", outputFile.getPath());
            }
        }


    }

}
