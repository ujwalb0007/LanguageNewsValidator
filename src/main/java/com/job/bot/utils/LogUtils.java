package com.job.bot.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {

    public static void logToFile(String filePath, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("[" + timestamp + "] " + message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
