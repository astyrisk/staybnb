package com.staybnb.components;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchForm extends BaseComponent {
    private static final String SET_DATE_INPUT_JS = "com/staybnb/scripts/setDateInput.js";

    public SearchForm(WebDriver driver) {
        super(driver);
    }

    public void expand() {
        waitForElementClickable(Locators.SearchBar.COMPACT_SEARCH_BTN).click();
    }

    public boolean isExpanded() {
        return isDisplayed(Locators.SearchBar.EXPANDED_FORM);
    }

    public boolean isCompactButtonDisplayed() {
        return isDisplayed(Locators.SearchBar.COMPACT_SEARCH_BTN);
    }

    public void enterDestination(String city) {
        WebElement input = waitForElementVisible(Locators.SearchBar.DESTINATION_INPUT);
        input.clear();
        input.sendKeys(city);
    }

    public void submit() {
        waitForElementClickable(Locators.SearchBar.SEARCH_SUBMIT_BTN).click();
    }

    public void searchForCity(String city) {
        expand();
        enterDestination(city);
        submit();
        waitForUrlContains("city=");
    }

    public void setCheckInDate(String isoDate) {
        setDateInput(Locators.SearchBar.CHECK_IN_INPUT, isoDate);
    }

    public void setCheckOutDate(String isoDate) {
        setDateInput(Locators.SearchBar.CHECK_OUT_INPUT, isoDate);
    }

    public String getCheckInMinAttribute() {
        return waitForElementVisible(Locators.SearchBar.CHECK_IN_INPUT).getAttribute("min");
    }

    public String getCheckOutMinAttribute() {
        return waitForElementVisible(Locators.SearchBar.CHECK_OUT_INPUT).getAttribute("min");
    }

    public void waitForCheckOutMinToBe(String expectedMin) {
        wait.until(ExpectedConditions.attributeToBe(Locators.SearchBar.CHECK_OUT_INPUT, "min", expectedMin));
    }

    public int getGuestsCount() {
        return Integer.parseInt(waitForElementVisible(Locators.SearchBar.GUESTS_DISPLAY).getText());
    }

    public void incrementGuests(int times) {
        for (int i = 0; i < times; i++) {
            waitForElementClickable(Locators.SearchBar.GUESTS_INCREMENT_BTN).click();
        }
    }

    public void decrementGuests(int times) {
        for (int i = 0; i < times; i++) {
            waitForElementClickable(Locators.SearchBar.GUESTS_DECREMENT_BTN).click();
        }
    }

    public void searchWithDates(String checkIn, String checkOut) {
        expand();
        setCheckInDate(checkIn);
        setCheckOutDate(checkOut);
        submit();
        waitForUrlContains("checkIn=");
    }

    public void searchWithGuests(int targetGuests) {
        expand();
        int current = getGuestsCount();
        if (targetGuests > current) {
            incrementGuests(targetGuests - current);
        } else if (targetGuests < current) {
            decrementGuests(current - targetGuests);
        }
        submit();
        waitForUrlContains("guests=");
    }

    private void setDateInput(By locator, String isoDate) {
        WebElement input = waitForElementVisible(locator);
        String script = loadScript(SET_DATE_INPUT_JS);
        ((JavascriptExecutor) driver).executeScript(script, input, isoDate);
    }
}
