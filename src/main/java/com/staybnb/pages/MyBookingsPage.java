package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MyBookingsPage extends BasePage {

    public MyBookingsPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(AppConstants.BOOKINGS_URL);
        waitForElementVisible(Locators.MyBookings.PAGE_TITLE);
    }

    public String getBookingIdByPropertyTitle(String propertyTitle) {
        List<WebElement> cards = waitForElementsPresent(Locators.MyBookings.BOOKING_CARD);
        return cards.stream()
                .filter(card -> {
                    List<WebElement> titles = card.findElements(Locators.MyBookings.CARD_TITLE);
                    return !titles.isEmpty() && titles.get(0).getText().equals(propertyTitle);
                })
                .findFirst()
                .map(card -> {
                    String href = card.getAttribute("href");
                    return href.substring(href.lastIndexOf('/') + 1);
                })
                .orElse(null);
    }

    public void cancelBookingViaApi(String bookingId) {
        apiRequest().put("/bookings/" + bookingId + "/cancel");
    }
}
