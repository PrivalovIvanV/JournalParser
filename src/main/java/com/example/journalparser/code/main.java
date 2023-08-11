package com.example.journalparser.code;

import com.example.journalparser.code.AuthorAndArticle;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.im4java.core.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.http.HttpClient.Version.HTTP_2;

public class main {

//    private static int count = 66;

    public static void main1(String[] args) throws URISyntaxException, IOException, InterruptedException {

//        String str = "Электрические станции.\n\nЭрган Министерства электростанций СССР.\n\nСОДЕРЖАНИЕ\n\nРапорт товарищу И. В. Сталину, Министру\nэлектростанций СССР Д. Жимерина.\n\nОбращение работников Мосэнерго к рабочим,\nинженерам и техникам электростанций и\nсетей — Министерства — электростанций.\n\nСор са чсы пое оЫ оН ч оче.\n\nПередовая — За досрочное выполнение пос-\nлевоенной Сталинской пятилетки.\n\nГ. Г. Гагарин—Влияние режима работы\nкотла на циркуляцию.\n\nБ. Ф. Добкин и В. Г. Стратонов — О про-\nдолжительности работы паровых турбин\nмежду капитальными ремонтами.\n\nЛ. Д. Берман—Прямоточное охлаждение\nконденсаторов при ограниченном количестве воды.\n\nА. И. Бронников — О централизованном испол-\nнении энергосистемами ремонта.\n\nН. Ф. Кираковский — Зависимость расхожде-\nния замков от изменения диаметра кольцевого штифтмасса.\n\nЯ. Б. Воловник и Н. А. Реговин—Монтаж\nкотельного агрегата параллельно с ведением работ по сооружению здания котельной.\n\nЛ. Д. Стернинсон—Характеристики энергосистемы как объекта автоматического регулирования частоты.\n\nЯ. С. Колин — Эффективные методы профилактических испытаний изоляции обмоток генераторов.\n\nМ. И. Царев—Усовершенствование дифференциальной защиты трансформаторов с реле КР-121.\n\nОБМЕН СТРОИТЕЛЬНО-МОНТАЖНЫМ ОПЫТОМ.\n\nС. П. Гончаров — Устройство большого монтажного проема во временной торцевой стене котельной.\n\nС. Т. Слюсарев — Восстановление подвесок барабанов.\n\n*—Новые марки электродов для сварки молибденовых сталей."
//
//

//        System.out.println(group);

//        System.out.println(body + "\n" + res.statusCode());
    }

//    public static void main(String[] args) throws IM4JavaException, IOException, InterruptedException, URISyntaxException {
////        String s = cutImage("/home/ivan/Загрузки/журналы/results/00001.jpg");
//        String s1 = extractText("/home/ivan/Изображения/qq.png");
//        String s2 = chatGpt(s1, false, "Исправь грамматические ошибки. Отформатируй текст. Удали переносы строки. Замени двойные тире - одинарными");
//        String s = chatGpt(s2, true, "Удали непонятные слова. Убери лишние знаки и пробелы. Если в конце строки есть цифры, то удали их");
//        List<AuthorAndArticle> list = extractAuthor(s);
//
//        list.forEach(System.out::println);

//    }

    public static String chatGpt(String textFile, boolean isRepeat, String requestt, int count) throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newBuilder()
                .version(HTTP_2)
                .build();

        String partOfRequest;
        if (!isRepeat) {
            StringBuffer sb = new StringBuffer();
            Files.lines(Paths.get(textFile), StandardCharsets.UTF_8).forEach(string -> sb.append(string).append("\n"));
//            System.out.println("----------------------------------------------------------------------\n");
//            System.out.println(sb + "\n");
//            System.out.println("----------------------------------------------------------------------\n");
//            String template = "{\"prompt\": \"Исправь грамматические ошибки, убери пустые строки, лишние знаки и пробелы\n\n%s\",\"options\":{\"parentMessageId\":\"chatcmpl-7j4F9QXsmv2QCoHjgiObQy7pMZWZM\"}}";
            String string1 = sb.toString();
            partOfRequest = string1.replaceAll("\\n", "\\\\n");
        } else {
            partOfRequest = textFile.replaceAll("\\n", "\\\\n");
            partOfRequest = partOfRequest.replaceAll("\"", "");
        }
//        System.out.println(partOfRequest);
//        String string = String.format(template, sb);
//        String string = "{\"text\":\"" + requestt + "\\n \\n" + partOfRequest + "\",\"category\":\"5be3c43f8bc740b792cce30cebdd861c\",\"model\":\"12cf0aaece3f4c27846aeb9c852dc0f9\",\"model_params\":{},\"topic_id\":null}";
        String string = "{\"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"" + requestt + "\\n \\n" + partOfRequest + "\"}],\n" +
                "     \"temperature\": 0.7}";
//        System.out.println(string);


        HttpRequest request = HttpRequest.newBuilder()
                .version(HTTP_2)
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/115.0")
//                .header("Accept", "text/event-stream")
//                .header("Accept-Encoding", "gzip, deflate, br")
//                .header("Origin", "https://beta.theb.ai")
//                .header("Referer", "https://beta.theb.ai/home   ")
//                .header("access-control-allow-origin", "https://beta.theb.ai")
                .header("Authorization", "Bearer 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")
//                .header("Accept-Language", "en-US,en;q=0.5")
                .POST(HttpRequest.BodyPublishers.ofString(string))
//                .uri(new URI("https://chatbot.theb.ai/api/chat-process"))
//                .uri(new URI("https://beta.theb.ai/api/conversation?org_id=2addf250-64ec-4a3a-8f4f-b857e3fd4596"))
                .uri(new URI("https://api.openai.com/v1/chat/completions"))

                .build();
        HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = res.body();
//        String[] roles = body.split("data");
//        int i = roles.length - 2;
//        System.out.println(body);
//        String role = (roles[i]).split("\"}")[0];
//
//        Pattern pattern = Pattern.compile(": \\{\"content\": \"((.|\\n)*)(\"}\\n)");
//        Matcher matcher = pattern.matcher(role);
//        boolean b = matcher.find();
//        if (!b){
//            System.out.println("ERROR: chatGPT не обработал файл " + count);
//            return "Ответ_хуйня";
//        }
//        String group = matcher.group(1);
//        Thread.sleep(1000);
        String s = extractContentNewApi(body);
        if (s.equals("Ответ_хуйня")){
            System.out.println("ERROR: chatGPT не обработал файл " + count + "\nbody: " + body);
        } else {
//            System.out.println(s);
        }
        return s;

//        System.out.println("before replace " + group);
//        String replaceAll = group.replaceAll("\\\\n", "\n").replaceAll("\\n\\n", "\n");
//        System.out.println("with replace: " + replaceAll);
//        System.out.println(replaceAll);
//        return replaceAll;
    }


    public static String extractContent(String str) {

//        String string = ": {\"content\": \"Рапорт товарищу И. В. Сталину Министра электростанций СССР Д. Жимерина. 3\\nОбращение работников Мосэнерго к рабочим, инженерам и техникам электростанций и сетей Министерства электростанций СССР. З\\nПередовая — За досрочное выполнение послевоенной Сталинской пятилетки... 5\\nГ. Г. Гагарин — Влияние режима работы котла на циркуляцию.. 9\\nБ. Ф. Добкин и Н. Г. Стратонов — О продолжительности работы паровых турбин между капитальными ремонтами... 15\\nЛ. Д. Берман — Прямоточное охлаждение конденсаторов при ограниченном количестве воды... 18\\nА. И. Бронников — О централизованном исполнении энергосистемами ремонта... 24\\nН. Ф. Кираковский — Зависимость расхождения замков от изменения диаметра кольцевого штихмасса... 26\\nЯ. Б. Воловник и Н. А. Роговин — Монтаж котельного агрегата параллельно с ведением работ по сооружению здания котельной... 28\"}\n" +
//                "\n" +
//                "event: end";

        Pattern pattern = Pattern.compile("content\": \"(.*)");
        Matcher matcher = pattern.matcher(str);
        boolean b = matcher.find();
        if (!b) {
//            System.out.println("ERROR: chatGPT не обработал файл " + count);
            return "Ответ_хуйня";
        }
        String replaceAll = matcher.group(1).replaceAll("\\\\n", "\n").replaceAll("\\n\\n", "\n");
        return replaceAll;

    }

    @SneakyThrows
    public static String extractContentNewApi(String str) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(str);
        JsonNode jsonNode1 = jsonNode.findValue("content");
        try{
            return jsonNode1.asText();
        }catch (Exception e){
            return "Ответ_хуйня";
        }

    }








































    public static List<AuthorAndArticle> extractAuthor(String string) {
        String s = string;
        String[] split = s.split("\\n");
        List<String> list = Arrays.asList(split);

        ArrayList<AuthorAndArticle> articles = new ArrayList<>();
        Pattern compile = Pattern.compile("(^([А-я]\\. ){1,3}[А-я]{3,})([^\\n]*)");
        list.forEach(s1 -> {
            Matcher matcher1 = compile.matcher(s1);
            if (matcher1.find()) {
                AuthorAndArticle authorAndArticle = new AuthorAndArticle();
                authorAndArticle.setAuthor(matcher1.group(1));
                authorAndArticle.setArticle(matcher1.group(3));
                articles.add(authorAndArticle);
            } else {
                System.out.println("не смог обработать строку " + s1);
            }

        });
        return articles;
    }



    public static String cutImage(String imageFile) throws IM4JavaException, IOException, InterruptedException {
        ConvertCmd cmd = new ConvertCmd();

        Info imageInfo = new Info("/home/ivan/Загрузки/журналы/results/00001.jpg", true);
        System.out.println("Format: " + imageInfo.getImageFormat());
        System.out.println("Width: " + imageInfo.getImageWidth());
        System.out.println("Height: " + imageInfo.getImageHeight());
        System.out.println("Geometry: " + imageInfo.getImageGeometry());
        System.out.println("Depth: " + imageInfo.getImageDepth());
        System.out.println("Class: " + imageInfo.getImageClass());

        IMOperation op = new IMOperation();

        int wight = (imageInfo.getImageWidth() / 2) + 1;

        op.addImage(imageFile);
        op.crop(wight, imageInfo.getImageHeight());
        op.addImage("/home/ivan/Загрузки/журналы/results/готово/00001.jpg");


        cmd.run(op);
        return "/home/ivan/Загрузки/журналы/results/готово/00001-0.jpg";
    }

    public static String extractText(String imagePath) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process exec = rt.exec(String.format("tesseract %s /home/ivan/Загрузки/in -l rus --psm 1", imagePath));
        try {
            exec.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "/home/ivan/Загрузки/in.txt";

    }
}
