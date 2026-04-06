package com.staybnb.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        if (isHeadlessMode()) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(AppConstants.MEDIUM_WAIT));
        driver.manage().window().maximize();
        driverThread.set(driver);
        return driver;
    }

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }

    private static boolean isHeadlessMode() {
        return "true".equals(System.getenv("GITHUB_ACTIONS"))
                || System.getenv("JENKINS_URL") != null
                || "true".equalsIgnoreCase(System.getProperty("headless"))
                || "true".equalsIgnoreCase(ConfigProperties.get("headless", "false"));
    }
}
