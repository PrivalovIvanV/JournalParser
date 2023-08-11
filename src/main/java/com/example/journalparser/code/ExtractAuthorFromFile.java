package com.example.journalparser.code;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ExtractAuthorFromFile {


    @PersistenceContext
    private EntityManager manager;

    private String homeFolder = "/home/ivan/Загрузки/журналы/good/";
    private Data data;
    private List<String> extractStrings = new ArrayList<>();


    @Transactional
    public void extract(int dataStart){
        data = new Data(dataStart);

        File homeFolder = new File(this.homeFolder);
        List<String> files = Arrays.asList(homeFolder.listFiles())
                .stream()
                .map(File::getName)
                .sorted((s, t1) -> {
                    int firstElement = Integer.valueOf(s.split("\\.")[0]);
                    int secondElement = Integer.valueOf(t1.split("\\.")[0]);

                    return firstElement > secondElement ? 1 : -1;
                })
                .collect(Collectors.toList());
        files.forEach(System.out::println);


        Pattern pattern = Pattern.compile("(([А-ЯA-Z]\\. ?){1,3}[А-я]{3,}[^—-]*)(.+?(?=\\W\\.)[а-я]?)", Pattern.DOTALL);
        files.forEach(file -> {
            String input = read(homeFolder + "/" + file);
            Matcher matcher = pattern.matcher(input);
            System.out.printf("год %s выпуск %s", data.getCurrentData(), data.currentNumber);
            String formatted = String.format("год %s выпуск %s", data.getCurrentData(), data.currentNumber);
            Query nativeQuery = manager.createNativeQuery("insert into journals(author, article) values (' ', ' ')");
            Query nativeQuery1 = manager.createNativeQuery("insert into journals(author) values ('" + formatted + "')");
            nativeQuery.executeUpdate();
            nativeQuery1.executeUpdate();

            while (matcher.find()){
                int groupCount = matcher.groupCount();
                if (groupCount == 3){
                    String author = matcher.group(1)
                            .replaceAll(":", "")
                            .replaceAll("'", "")
                            .replaceAll("\\n", " ")
                            .replaceAll("\\{", "");
                    if (author.length() > 150){
                        continue;
                    }
                    String article = matcher.group(3)
                            .replaceAll("\\n", " ")
                            .replaceAll(":", "")
                            .replaceAll("'", "")
                            .replaceAll("\\{", "");

                    extractStrings.add(author);
                    extractStrings.add(article);
                    Query nativeQuery2 = manager.createNativeQuery("insert into journals(author, article) values ('" + author + "', '" + article + "')");
                    nativeQuery2.executeUpdate();
//                    System.out.printf("%s \n %s \n\n\n", author, article);

                }
            }
            manager.flush();
        });

    }

    private String read(String file){
        data.increment();
        try {
            return Files.readString(Path.of(file));
        } catch (IOException e) {
            System.out.printf("пустой файл %s \n", file);
        }
        return "";
    }


    class Data{

        private int currentData;
        private int currentNumber = 1;
        private boolean isFirstIncrement = true;

        public Data(int currentData, int currentNumber) {
            this.currentData = currentData;
            this.currentNumber = currentNumber;
        }

        public Data(int currentData) {
            this.currentData = currentData;
        }

        public void increment(){
            if (!isFirstIncrement) {
                currentNumber++;
                if (currentNumber == 13) {
                    currentData++;
                    currentNumber = 1;
                }
            } else {
                isFirstIncrement = false;
            }
        }

        public int getCurrentData() {
            return currentData;
        }

        public int getCurrentNumber() {
            return currentNumber;
        }
    }


}
