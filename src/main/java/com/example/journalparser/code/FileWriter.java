package com.example.journalparser.code;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class FileWriter {

    public void println(String value, File outputFile) throws IOException {
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        Files.write(outputFile.toPath(), value.getBytes(), StandardOpenOption.APPEND);

    }

    public  void write(String value, File outputFile) throws IOException {
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        Files.write(outputFile.toPath(), value.getBytes(), StandardOpenOption.WRITE);

    }

}
