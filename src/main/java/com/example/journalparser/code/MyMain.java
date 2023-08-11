package com.example.journalparser.code;

import lombok.SneakyThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class MyMain {

    private static final String outputFolder = "/home/ivan/Загрузки/журналы/numder/";
    private static final String inputFolder = "/home/ivan/Загрузки/fileInput.txt";
    private static int forCutFile =1;
    private static int countForChatGpt = 67;


    public static void main(String[] args) throws IOException {

//        chatGpt(1, "Исправь грамматические ошибки. Отформатируй текст. Удали непонятные слова. Убери лишние знаки, цифры и пробелы. Фамилию с инициалами выводи с новой строки");
//        int i = cutFile();
//        System.out.println(i);
        IntStream.range(67, 83)
                .forEach(e -> chatGpt(e, "Исправь грамматические ошибки, убери переносы и лишние знаки и пробелы"));

    }

    @SneakyThrows
    public static void chatGpt(int filePath, String request) {
        StringBuffer buffer = new StringBuffer();
        try {
            Files.lines(Paths.get(outputFolder+ filePath+ ".txt"), StandardCharsets.UTF_8).forEach(string -> buffer.append(string).append("\n"));

        }catch (Exception e){
            countForChatGpt++;
            System.out.println(countForChatGpt + " false он оказался пустой");
            return;
        }
        String s = " ";
        s = main.chatGpt(buffer.toString(), true, request, countForChatGpt);
        if (s.equals("Ответ_хуйня")) {
            System.out.println(countForChatGpt + " false");
        } else {
            System.out.println(countForChatGpt + " true");
            long count = s.lines().count();
            if (count < 10){
                System.out.printf("TRACE: файл %s небыл перезаписан, количество строк после нейросети стало %s, а было %s", countForChatGpt, count, buffer.toString().lines());
            } else {
                write(s, filePath);

            }
        }
//        System.out.println(s);
        countForChatGpt++;
//        Thread.sleep(10000);

    }

    public static int cutFile() throws IOException {
        LinkedList<StringBuffer> list = new LinkedList<>();
        list.add(new StringBuffer());
        Pattern pattern = Pattern.compile("Пролетарии");
        Files.lines(Paths.get(inputFolder), StandardCharsets.UTF_8).forEach(string -> {
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
                try {
                    write(str, forCutFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.printf("Error:  %s в одну строку%n", forCutFile);
            }
            forCutFile++;
        });
        return list.size();

    }


    public static void println(String string, int fileName) throws IOException {
        File file = new File(outputFolder + fileName + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(Paths.get(outputFolder + fileName + ".txt"), string.getBytes(), StandardOpenOption.APPEND);

    }

    public static void write(String string, int fileName) throws IOException {
        File file = new File(outputFolder + fileName + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        Files.write(Paths.get(outputFolder + fileName + ".txt"), string.getBytes(), StandardOpenOption.WRITE);

    }


}
