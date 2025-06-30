package com.job.bot;

import io.github.bonigarcia.wdm.WebDriverManager;
import com.job.bot.utils.ScreenshotUtils;
import com.job.bot.utils.LogUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class NewsLanguageValidator {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Modern headless mode
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox"); // Required in CI
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        // This prevents the 'user-data-dir' error in CI
        options.addArguments("--user-data-dir=/tmp/chrome-user-" + System.currentTimeMillis());
        WebDriver driver = new ChromeDriver(options);

        // Ensure directories exist
        new File("screenshots").mkdirs();
        new File("reports").mkdirs();

        try {
            driver.get("https://inshorts.com/en/read");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("span[itemprop='headline']")));

            List<WebElement> headlines = driver.findElements(
                    By.cssSelector("span[itemprop='headline']"));

            System.out.println("Total headlines found: " + headlines.size());
            LogUtils.logToFile("reports/log_" + LocalDate.now() + ".txt", "Total headlines found: " + headlines.size());

            int count = 1;
            for (WebElement headline : headlines) {
                String text = headline.getText().trim();
                String result = isEnglish(text) ? "EN" : "NOT_EN";
                String status = "[" + result + "] " + text;

                // Log to file
                LogUtils.logToFile("reports/log_" + LocalDate.now() + ".txt", status);

                // Screenshot each headline
                String filename = "screenshots/" + result + "_" + count + "_success.png";
                captureElementScreenshot(driver, headline, filename);

                System.out.println(status);
                count++;
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "screenshots/Error_Screenshot.png");
            LogUtils.logToFile("reports/log_" + LocalDate.now() + ".txt", "Error: " + e.getMessage());

        } finally {
            driver.quit();
        }
    }

    public static boolean isEnglish(String text) {
        return text.chars().allMatch(c -> c < 128);
    }

    public static void captureElementScreenshot(WebDriver driver, WebElement element, String path) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            try {
                Thread.sleep(500); // allow the scroll to complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(screenshot);

            org.openqa.selenium.Point point = element.getLocation();
            int x = point.getX();
            int y = point.getY();
            int eleWidth = element.getSize().getWidth();
            int eleHeight = element.getSize().getHeight();

            BufferedImage eleScreenshot = fullImg.getSubimage(x, y, eleWidth, eleHeight);

        } catch (IOException | WebDriverException e) {
            e.printStackTrace();
        }
    }
}
