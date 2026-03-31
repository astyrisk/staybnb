package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import com.staybnb.config.Constants;

public class HomePage extends BasePage {
    private final String PAGE_URL = Constants.HOME_URL;

    private By heroSection = Locators.Home.HERO_SECTION;
    private By heroHeadline = Locators.Home.HERO_HEADLINE;
    private By categoryBar = Locators.Home.CATEGORY_BAR;
    private By categoryIcons = Locators.Home.CATEGORY_ICONS;
    private By propertyGrid = Locators.Home.PROPERTY_GRID;
    private By propertyCards = Locators.Home.PROPERTY_CARDS;

    private By cardImage = Locators.Home.CARD_IMAGE;
    private By cardTitle = Locators.Home.CARD_TITLE;
    private By cardLocation = Locators.Home.CARD_LOCATION;
    private By cardPrice = Locators.Home.CARD_PRICE;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(PAGE_URL);
        waitForHomeToLoad();
    }

    public boolean isHeroSectionDisplayed() {
        return isDisplayed(heroSection);
    }

    public String getHeroHeadlineText() {
        return waitForElementVisible(heroHeadline).getText();
    }

    public String getHeroBackgroundImage() {
        return waitForElementVisible(heroSection).getCssValue("background-image");
    }

    public boolean isCategoryBarDisplayed() {
        return isDisplayed(categoryBar);
    }

    public List<WebElement> getCategoryIcons() {
        return waitForElementsPresent(categoryIcons);
    }

    public List<WebElement> getPropertyCards() {
        return waitForElementsPresent(propertyCards);
    }

    public boolean isCardDetailsComplete(WebElement card) {
        try {
            boolean hasImage = card.findElement(cardImage).isDisplayed();
            boolean hasTitle = !card.findElement(cardTitle).getText().isEmpty();
            boolean hasLocation = !card.findElement(cardLocation).getText().isEmpty();
            boolean hasPrice = card.findElement(cardPrice).getText().contains("/ night");
            return hasImage && hasTitle && hasLocation && hasPrice ;
        } catch (Exception e) {
            return false;
        }
    }

    public int getGridColumnCount() {
        WebElement grid = driver.findElement(propertyGrid);
        String gridTemplate = grid.getCssValue("grid-template-columns");
        if (gridTemplate == null || gridTemplate.isEmpty()) return 0;
        return gridTemplate.split(" ").length;
    }

    public void waitForGridColumns(int expectedCount) {
        wait.until(driver -> getGridColumnCount() == expectedCount);
    }

    public void scrollFeaturedPropertiesIntoView() {
        // keeps JS out of tests
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)");
        waitForElementsPresent(propertyCards);
    }

    private void waitForHomeToLoad() {
        wait.until(d ->
                d.findElements(heroSection).size() > 0 ||
                d.findElements(propertyGrid).size() > 0
        );
    }
}
