package com.staybnb.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public final class DriverFactory {
    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver createDriver() {
        boolean headless = isHeadlessMode();
        log.debug("Creating ChromeDriver (headless={})", headless);
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WaitConstants.MEDIUM_WAIT));
        if (headless) {
            driver.manage().window().setSize(new Dimension(WaitConstants.WIDE_DESKTOP_WIDTH, WaitConstants.WIDE_DESKTOP_HEIGHT));
        } else {
            driver.manage().window().maximize();
        }
        driverThread.set(driver);
        log.debug("ChromeDriver created for thread [{}]", Thread.currentThread().getName());
        return driver;
    }

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            log.debug("Quitting ChromeDriver for thread [{}]", Thread.currentThread().getName());
            try {
                driver.quit();
            } finally {
                driverThread.remove();
            }
        }
    }

    private static boolean isHeadlessMode() {
        return "true".equals(System.getenv("GITHUB_ACTIONS"))
                || System.getenv("JENKINS_URL") != null
                || "true".equalsIgnoreCase(System.getProperty("headless"))
                || "true".equalsIgnoreCase(ConfigProperties.get("headless", "false"));
    }
}
