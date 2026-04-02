package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import com.staybnb.config.Constants;

public class HomePage extends BasePage {
    private final String PAGE_URL = Constants.HOME_URL;

    private By heroSection = Locators.Home.HERO_SECTION;
    private By heroHeadline = Locators.Home.HERO_HEADLINE;
    private By categoryBar = Locators.Home.CATEGORY_BAR;
    private By categoryChips = Locators.Home.CATEGORY_CHIPS;
    private By activeCategoryChip = Locators.Home.ACTIVE_CATEGORY_CHIP;
    private By categoryIcons = Locators.Home.CATEGORY_ICONS;
    private By propertiesCount = Locators.Home.PROPERTIES_COUNT;
    private By propertyGrid = Locators.Home.PROPERTY_GRID;
    private By propertyCards = Locators.Home.PROPERTY_CARDS;

    private By cardImage = Locators.Home.CARD_IMAGE;
    private By cardTitle = Locators.Home.CARD_TITLE;
    private By cardLocation = Locators.Home.CARD_LOCATION;
    private By cardPrice = Locators.Home.CARD_PRICE;

    private static final String GET_ELEMENT_DIRECT_TEXT_JS_RESOURCE = "com/staybnb/scripts/getElementDirectText.js";

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

    public List<WebElement> getCategoryChips() {
        return waitForElementsPresent(categoryChips);
    }

    public boolean hasCategoryChipNamed(String categoryName) {
        String expected = categoryName == null ? "" : categoryName.trim();
        if (expected.isEmpty()) {
            return false;
        }
        By chip = By.xpath("//div[contains(@class,'categories-bar')]//button[contains(@class,'category-chip')][text()[normalize-space()='" + expected + "']]");
        return !driver.findElements(chip).isEmpty();
    }

    public void clickCategoryByName(String categoryName) {
        String expected = categoryName == null ? "" : categoryName.trim();
        if (expected.isEmpty()) {
            throw new IllegalArgumentException("Category name must not be blank");
        }
        By chip = By.xpath("//div[contains(@class,'categories-bar')]//button[contains(@class,'category-chip')][text()[normalize-space()='" + expected + "']]");

        waitForElementClickable(chip).click();
        waitForActiveCategoryToBe(expected);
    }

    public String getActiveCategoryName() {
        WebElement chip = waitForElementVisible(activeCategoryChip);
        String script = loadJavascriptResource(GET_ELEMENT_DIRECT_TEXT_JS_RESOURCE);
        Object result = ((JavascriptExecutor) driver).executeScript(script, chip);
        return Optional.ofNullable(result).map(Object::toString).orElse("").trim();
    }

    public void waitForActiveCategoryToBe(String expectedCategoryName) {
        String expected = expectedCategoryName == null ? "" : expectedCategoryName.trim();
        wait.until(d -> expected.equalsIgnoreCase(getActiveCategoryName()));
    }

    public List<WebElement> getCategoryIcons() {
        return waitForElementsPresent(categoryIcons);
    }

    public boolean isCategoryBarHorizontallyScrollable() {
        WebElement bar = waitForElementVisible(categoryBar);
        Object result = ((JavascriptExecutor) driver).executeScript(
                "var el=arguments[0];" +
                        "var style=window.getComputedStyle(el);" +
                        "var overflowX=style ? style.overflowX : '';" +
                        "return { scrollable: (el.scrollWidth>el.clientWidth), overflowX: overflowX };",
                bar
        );
        if (result instanceof java.util.Map<?, ?> map) {
            Object scrollable = map.get("scrollable");
            Object overflowX = map.get("overflowX");
            boolean canScroll = Boolean.TRUE.equals(scrollable);
            String ox = overflowX == null ? "" : overflowX.toString().toLowerCase();
            return canScroll && (ox.contains("auto") || ox.contains("scroll"));
        }
        return false;
    }

    public String getPropertiesCountText() {
        return waitForElementVisible(propertiesCount).getText().trim();
    }

    public void waitForPropertiesCountToContain(String expectedText) {
        String expected = expectedText == null ? "" : expectedText.trim();
        wait.until(d -> getPropertiesCountText().contains(expected));
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

    private String loadJavascriptResource(String resourcePath) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalStateException("Missing JS resource on classpath: " + resourcePath);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JS resource on classpath: " + resourcePath, e);
        }
    }

    private void waitForHomeToLoad() {
        wait.until(d ->
                d.findElements(heroSection).size() > 0 ||
                d.findElements(propertyGrid).size() > 0
        );
    }
}
