package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.staybnb.config.AppConstants;

import java.util.List;
import java.util.Optional;

public class HomePage extends BasePage {
    private static final String GET_ELEMENT_DIRECT_TEXT_JS  = "com/staybnb/scripts/getElementDirectText.js";
    private static final String IS_CATEGORY_BAR_SCROLLABLE_JS = "com/staybnb/scripts/isCategoryBarScrollable.js";
    private static final String SCROLL_BY_Y500_JS           = "com/staybnb/scripts/scrollByY500.js";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(AppConstants.HOME_URL);
        waitForHomeToLoad();
    }

    public boolean isHeroSectionDisplayed() {
        return isDisplayed(Locators.Home.HERO_SECTION);
    }

    public String getHeroHeadlineText() {
        return waitForElementVisible(Locators.Home.HERO_HEADLINE).getText();
    }

    public String getHeroBackgroundImage() {
        return waitForElementVisible(Locators.Home.HERO_SECTION).getCssValue("background-image");
    }

    public boolean isCategoryBarDisplayed() {
        return isDisplayed(Locators.Home.CATEGORY_BAR);
    }

    public boolean hasCategoryChipNamed(String categoryName) {
        String name = normalizeCategory(categoryName);
        if (name.isEmpty()) return false;
        return !driver.findElements(categoryChipLocator(name)).isEmpty();
    }

    public void clickCategoryByName(String categoryName) {
        String name = normalizeCategory(categoryName);
        if (name.isEmpty()) throw new IllegalArgumentException("Category name must not be blank");
        waitForElementClickable(categoryChipLocator(name)).click();
    }

    public String getActiveCategoryName() {
        WebElement chip = waitForElementVisible(Locators.Home.ACTIVE_CATEGORY_CHIP);
        String script = loadScript(GET_ELEMENT_DIRECT_TEXT_JS);
        Object result = ((JavascriptExecutor) driver).executeScript(script, chip);
        return Optional.ofNullable(result).map(Object::toString).orElse("").trim();
    }

    public List<WebElement> getCategoryIcons() {
        return waitForElementsPresent(Locators.Home.CATEGORY_ICONS);
    }

    public boolean isCategoryBarHorizontallyScrollable() {
        WebElement bar = waitForElementVisible(Locators.Home.CATEGORY_BAR);
        String script = loadScript(IS_CATEGORY_BAR_SCROLLABLE_JS);
        return Boolean.TRUE.equals(((JavascriptExecutor) driver).executeScript(script, bar));
    }

    public String getPropertiesCountText() {
        return waitForElementVisible(Locators.Home.PROPERTIES_COUNT).getText().trim();
    }

    public void waitForPropertiesCountToContain(String expectedText) {
        String expected = expectedText == null ? "" : expectedText.trim();
        wait.until(d -> getPropertiesCountText().contains(expected));
    }

    public List<WebElement> getPropertyCards() {
        return waitForElementsPresent(Locators.Home.PROPERTY_CARDS);
    }

    public boolean isCardDetailsComplete(WebElement card) {
        try {
            boolean hasImage    = card.findElement(Locators.Home.CARD_IMAGE).isDisplayed();
            boolean hasTitle    = !card.findElement(Locators.Home.CARD_TITLE).getText().isEmpty();
            boolean hasLocation = !card.findElement(Locators.Home.CARD_LOCATION).getText().isEmpty();
            boolean hasPrice    = card.findElement(Locators.Home.CARD_PRICE).getText().contains("/ night");
            return hasImage && hasTitle && hasLocation && hasPrice;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public int getGridColumnCount() {
        WebElement grid = driver.findElement(Locators.Home.PROPERTY_GRID);
        String gridTemplate = grid.getCssValue("grid-template-columns");
        if (gridTemplate == null || gridTemplate.isEmpty()) return 0;
        return gridTemplate.split(" ").length;
    }

    public void waitForGridColumns(int expectedCount) {
        wait.until(driver -> getGridColumnCount() == expectedCount);
    }

    public void scrollFeaturedPropertiesIntoView() {
        ((JavascriptExecutor) driver).executeScript(loadScript(SCROLL_BY_Y500_JS));
        waitForElementsPresent(Locators.Home.PROPERTY_CARDS);
    }

    private static String normalizeCategory(String categoryName) {
        return categoryName == null ? "" : categoryName.trim();
    }

    private static By categoryChipLocator(String name) {
        return By.xpath(
                "//div[contains(@class,'categories-bar')]" +
                "//button[contains(@class,'category-chip')][text()[normalize-space()='" + name + "']]"
        );
    }

    private void waitForHomeToLoad() {
        wait.until(d ->
                !d.findElements(Locators.Home.HERO_SECTION).isEmpty()
                || !d.findElements(Locators.Home.PROPERTY_GRID).isEmpty()
        );
    }
}
