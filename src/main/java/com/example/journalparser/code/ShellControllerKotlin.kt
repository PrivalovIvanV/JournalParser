package com.example.journalparser.code

import com.example.journalparser.code.myComponents.ChangeToUtf8
import com.example.journalparser.code.myComponents.FileParser
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import java.io.File


@ShellComponent
class ShellControllerKotlin(
        val transcoder: ChangeToUtf8,
        val fileCutter: FileParser,
        val ai: GptService) {

    private val defaultRequest = "Исправь грамматические ошибки, убери переносы и лишние знаки и пробелы"
    @ShellMethod(key = ["transcode"], value = "Transcode files from UTF-16")
    fun transcode(@ShellOption("-d") pathToFolder: String,
                  @ShellOption("-f") pathToFile: String): String {

        if (notNone(pathToFolder))
            if (isCorrect(pathToFolder, isFolder = true))
                transcoder.encodeFolder(pathToFolder)
            else
                return ("Название папки ${pathToFolder} не корректно")
        else
            if (notNone(pathToFile)) {
                isCorrect(pathToFile, isFile = true)
                transcoder.encodeFile(pathToFile)
            } else
                return ("Файл ${pathToFile} не существует")

        return "success";
    }

    @ShellMethod(key = ["cut"], value = "Cut file from yous pattern")
    fun cutFile(@ShellOption("-f", "--file") fileInput: String,
                @ShellOption("-p") pattern: String,
                @ShellOption("-F", "--folder") folderOutput: String,
                @ShellOption("-s", "--start", defaultValue = 1.toString()) start: Int,
                @ShellOption("-r", defaultValue = true.toString()) rewrite: Boolean): String {

        val firstCondition = isCorrect(fileInput, isFile = true)
        val secondCondition = isCorrect(folderOutput, isFolder = true)

        if (firstCondition && secondCondition) {
            val countParts = fileCutter.cutFile(fileInput, pattern, folderOutput, start, rewrite)
            return "File ${fileInput} was cutten on ${countParts} parts"
        }else
            return "Incorrect input parameter"
    }

    @ShellMethod(key = ["gpt"], value = "Processing file with chatGpt")
    fun chatGpt(@ShellOption("-f", "--file", defaultValue = "NONE") fileInput: String,
                @ShellOption("-F", "--folder", defaultValue = "NONE") folder: String,
                @ShellOption("-r", "--request", defaultValue = "NONE") query: String,
                @ShellOption("-s", defaultValue = 1.toString()) start: Int,
                @ShellOption("-e", defaultValue = 0.toString()) end: Int){

        val request = if (notNone(query)) query else defaultRequest

        if (isCorrect(fileInput, isFile = true)) {
            val file = File(fileInput)
            ai.chatGpt(file, request)
        }
        if (isCorrect(folder, isFolder = true)) {
            val file = File(folder)
            var stop = end;
            if (stop == 0)
                stop = file.listFiles().size

            ai.chatGpt(file.path, start, stop, request)
        }

    }




    private fun notNone(vararg fields: String) = fields.all { s -> !s.contains("none", ignoreCase = true) }

    private fun isCorrect(string: String,
                          isFolder: Boolean = false,
                          isFile: Boolean = false): Boolean {
        val file = File(string)
        val notNone = !string.contains("none", ignoreCase = true)
        var isConsistent = false
        if (isFolder)
            isConsistent = !file.name.contains(".")
        if (isFile)
            isConsistent = file.name.contains(".") && file.isFile
        return notNone && isConsistent;
    }


}