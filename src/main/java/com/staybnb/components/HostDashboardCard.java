package com.staybnb.components;

import com.staybnb.locators.Locators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HostDashboardCard extends BaseComponent {
    private final WebElement root;

    public HostDashboardCard(WebDriver driver, WebElement root) {
        super(driver);
        this.root = root;
    }

    public boolean hasThumbnail() {
        return !root.findElements(Locators.HostDashboard.CARD_IMAGE).isEmpty();
    }

    public String getTitle() {
        return root.findElement(Locators.HostDashboard.CARD_TITLE).getText();
    }

    public String getLocation() {
        return root.findElement(Locators.HostDashboard.CARD_LOCATION).getText();
    }

    public String getPrice() {
        return root.findElement(Locators.HostDashboard.CARD_PRICE).getText();
    }

    public String getStatus() {
        return root.findElement(Locators.HostDashboard.CARD_STATUS).getText();
    }

    public boolean hasRating() {
        return !root.findElements(Locators.HostDashboard.CARD_RATING).isEmpty();
    }

    public boolean hasEditAction() {
        return !root.findElements(Locators.HostDashboard.CARD_EDIT_BUTTON).isEmpty();
    }

    public boolean hasDeleteAction() {
        return !root.findElements(Locators.HostDashboard.CARD_DELETE_BUTTON).isEmpty();
    }

    public boolean hasPublishToggleAction() {
        return !root.findElements(Locators.HostDashboard.CARD_PUBLISH_TOGGLE).isEmpty();
    }

    public boolean isDetailDisplayed(String detailName) {
        return switch (detailName) {
            case "thumbnail"              -> hasThumbnail();
            case "title"                  -> !getTitle().isEmpty();
            case "location"               -> !getLocation().isEmpty();
            case "price per night"        -> getPrice().contains("/night");
            case "published or draft status" -> {
                String s = getStatus().trim();
                yield s.equalsIgnoreCase("Published") || s.equalsIgnoreCase("Draft");
            }
            case "rating"                 -> hasRating();
            default -> throw new IllegalArgumentException("Unsupported detail: " + detailName);
        };
    }

    public boolean hasAction(String actionName) {
        return switch (actionName) {
            case "edit"           -> hasEditAction();
            case "delete"         -> hasDeleteAction();
            case "publish toggle" -> hasPublishToggleAction();
            default -> throw new IllegalArgumentException("Unsupported action: " + actionName);
        };
    }

    public WebElement getRoot() {
        return root;
    }
}
