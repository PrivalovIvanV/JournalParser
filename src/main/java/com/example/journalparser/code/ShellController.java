package com.example.journalparser.code;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final ChangeToUtf8 transcoder;

    @ShellMethod(key = "transcode", value = "Transcode files from UTF-16")
    public String transcode(@ShellOption(value = {"-d", "--directory"})
                                 String pathToFolder,
                                 @ShellOption(value = {"-f", "--file"})
                                 String pathToFile){

        if (hasBeenIntroduced(pathToFolder)){
            transcoder.encodeFolder(pathToFolder);
            return "Success";
        } else {
            if (hasBeenIntroduced(pathToFile)){
                transcoder.encodeFile(pathToFile);
                return "Success";
            }
            return "Не корректный запрос";
        }
    }




    private boolean hasBeenIntroduced(String val){
        return val.equals("__NONE__");
    }

}
