package com.staybnb.components;

import com.staybnb.locators.Locators;
import com.staybnb.pages.PropertyDetailsPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyCard extends BaseComponent {
    private final WebElement root;

    public PropertyCard(WebDriver driver, WebElement root) {
        super(driver);
        this.root = root;
    }

    public boolean hasImage() {
        return !root.findElements(Locators.PropertyListing.CARD_IMAGE).isEmpty()
                && root.findElement(Locators.PropertyListing.CARD_IMAGE).isDisplayed();
    }

    public String getTitle() {
        return root.findElement(Locators.PropertyListing.CARD_TITLE).getText();
    }

    public String getLocation() {
        return root.findElement(Locators.PropertyListing.CARD_LOCATION).getText();
    }

    public String getPrice() {
        return root.findElement(Locators.PropertyListing.CARD_PRICE).getText();
    }

    public String getHref() {
        return root.getAttribute("href");
    }

    public int getPriceAmount() {
        String text = root.findElement(Locators.PropertyListing.CARD_PRICE_AMOUNT).getText();
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    public double getRating() {
        List<WebElement> ratingEls = root.findElements(Locators.PropertyListing.CARD_RATING);
        if (ratingEls.isEmpty()) return -1.0;
        Matcher m = Pattern.compile("(\\d+\\.\\d+)").matcher(ratingEls.getFirst().getText());
        return m.find() ? Double.parseDouble(m.group(1)) : -1.0;
    }

    public PropertyDetailsPage click() {
        root.click();
        return new PropertyDetailsPage(driver);
    }

    public PropertyCard clickFavorite() {
        WebElement btn = root.findElement(Locators.PropertyListing.CARD_FAVORITE_BTN);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        return this;
    }

    public boolean isComplete() {
        try {
            return root.findElement(Locators.PropertyListing.CARD_IMAGE).isDisplayed()
                    && !root.findElement(Locators.PropertyListing.CARD_TITLE).getText().isEmpty()
                    && !root.findElement(Locators.PropertyListing.CARD_LOCATION).getText().isEmpty()
                    && root.findElement(Locators.PropertyListing.CARD_PRICE).getText().contains("/ night");
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
}
