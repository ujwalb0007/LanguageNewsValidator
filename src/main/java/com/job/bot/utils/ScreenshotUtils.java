package com.job.bot.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "screenshots/";

    static {
        new File(SCREENSHOT_DIR).mkdirs();
    }

    public static void captureScreenshot(WebDriver driver, String namePrefix) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File destFile = new File(SCREENSHOT_DIR + namePrefix + "_" + timestamp + ".png");

        try {
            org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
            System.out.println(" Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println(" Error saving screenshot: " + e.getMessage());
        }
    }
}
