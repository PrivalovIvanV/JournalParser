package com.example.journalparser.code.myComponents;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class FileParser {


    private final FileWriter writer;


    @SneakyThrows
    public int cutFile(String filePath, String patternInStr, String outputFolder, int startWith, boolean rewrite) {
        outputFolder = outputFolder.endsWith("/") ? outputFolder : outputFolder + "/";

        Pattern pattern = Pattern.compile(patternInStr, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        File file = new File(filePath);
        return cut(file, pattern, outputFolder, startWith, rewrite);
    }


    private int cut(File inputFile, Pattern pattern, String outputFolder, int startWith, boolean rewrite) throws IOException {
        List<StringBuffer> list = parseFile(inputFile, pattern);
        int falledMatch = 0;
        File file = new File(outputFolder + startWith + ".txt");

        for (StringBuffer value : list) {
            String str = value.toString();
            if (str.lines().count() > 2) {
                if (file.exists() && !rewrite){
                    incrementFileName(file);
                }
                startWith++;
                writer.write(str, file);
            }
            else falledMatch++;
        }

        return list.size() - falledMatch;

    }

    @SneakyThrows
    private void incrementFileName(File file) {
        String folderPath = file.getParent() + "/";
        int currentFile = extractFileNumber(file) + 1;

        File nextFile = new File(folderPath + currentFile + ".txt");
        if (nextFile.exists()) {
            incrementFileName(nextFile);
        }
        File oldFile = new File(file.getPath());
        oldFile.renameTo(nextFile);
//        Thread.sleep(10);


    }


    private int extractFileNumber(File file) {
        return Integer.valueOf(file.getName().split("\\.")[0]);
    }

    @SneakyThrows
    private List<StringBuffer> parseFile(File file, Pattern pattern) {
        LinkedList<StringBuffer> list = new LinkedList<>();
        list.add(new StringBuffer());

        Files.lines(file.toPath(), StandardCharsets.UTF_8).forEach(string -> {
            Matcher matcher = pattern.matcher(string);
            boolean b = matcher.find();
            if (b) {
                list.add(new StringBuffer());
            } else {
                StringBuffer last = list.getLast();
                last.append(string).append("\n");
            }
        });

        return list;
    }

    private boolean isFolder(String path) {
        Pattern compile = Pattern.compile("\\/\\w{1,}\\..{2,}");
        Matcher matcher = compile.matcher(path);
        return !matcher.find();
    }

}
