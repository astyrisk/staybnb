package com.staybnb.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PropertyGrid extends BaseComponent {
    private static final By GRID_LOCATOR = By.className("property-grid");

    public PropertyGrid(WebDriver driver) {
        super(driver);
    }

    public int getColumnCount() {
        WebElement grid = driver.findElement(GRID_LOCATOR);
        String gridTemplate = grid.getCssValue("grid-template-columns");
        if (gridTemplate == null || gridTemplate.isEmpty() || gridTemplate.equals("none")) return 1;
        return gridTemplate.split(" ").length;
    }

    public void waitForColumns(int expectedCount) {
        wait.until(d -> getColumnCount() == expectedCount);
    }
}
