package com.example.journalparser.code;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FileParser {

    private FileWriter writer;


    @SneakyThrows
    public int cutFile(String filePath){
        Pattern pattern = Pattern.compile("Пролетарии");
        File file = new File(filePath);
        return cutFile(file, pattern, filePath);
    }

    @SneakyThrows
    public int cutFile(String filePath, Pattern pattern){
        File file = new File(filePath);
        return cutFile(file, pattern, file.getParent());
    }

    public int cutFile(File inputFile, Pattern pattern, String outputFolder) throws IOException {
        String name = inputFile.getName();
        LinkedList<StringBuffer> list = new LinkedList<>();
        list.add(new StringBuffer());

        Files.lines(inputFile.toPath(), StandardCharsets.UTF_8).forEach(string -> {
            Matcher matcher = pattern.matcher(string);
            boolean b = matcher.find();
            if (b) {
                list.add(new StringBuffer());
            } else {
                StringBuffer last = list.getLast();
                last.append(string).append("\n");
            }
        });

        list.forEach(string1 -> {
            String str = string1.toString();
            if (str.lines().count() > 2) {
//                try {
//                    new File(outputFolder +"/"+ name + ".txt");
//                    writer.write(str, outputFolder +"/"+ name);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
            } else {
                System.out.printf("Error: файл %s получился в одну строку", name);
            }
        });
        return list.size();

    }
}
