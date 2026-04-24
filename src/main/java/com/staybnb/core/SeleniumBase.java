package com.staybnb.core;

import com.staybnb.config.WaitConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public abstract class SeleniumBase {
    protected final Logger log = LogManager.getLogger(getClass());

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected SeleniumBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WaitConstants.MEDIUM_WAIT));
    }

    protected WebElement waitForElementVisible(By locator) {
        log.debug("Waiting for visible: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitForElementsPresent(By locator) {
        log.debug("Waiting for present: {}", locator);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected WebElement waitForElementClickable(By locator) {
        log.debug("Waiting for clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        log.debug("Clicking: {}", locator);
        driver.findElement(locator).click();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public void waitForUrlContains(String text) {
        log.debug("Waiting for URL to contain: {}", text);
        wait.until(ExpectedConditions.urlContains(text));
    }

    public boolean urlContains(String text) {
        try {
            waitForUrlContains(text);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitForUrlToBe(String url) {
        log.debug("Waiting for URL to be: {}", url);
        wait.until(ExpectedConditions.urlToBe(url));
    }

    public void acceptConfirmationIfPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            log.debug("Accepted browser confirmation dialog");
        } catch (TimeoutException ignored) {
        }
    }

    protected String loadScript(String resourcePath) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalStateException("Missing JS resource on classpath: " + resourcePath);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JS resource on classpath: " + resourcePath, e);
        }
    }
}
