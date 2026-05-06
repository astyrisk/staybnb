package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.config.TestDataConstants;
import com.staybnb.locators.Locators;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;



public class PropertyDetailsPage extends BasePage {

    public PropertyDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String propertyId) {
        super.navigateTo(AppConstants.PROPERTY_DETAILS_BASE_URL + propertyId);
        waitForDetailsToLoad();
    }

    public String getTitle() {
        return waitForElementVisible(
            Locators.PropertyDetails.DETAIL_TITLE
        ).getText();
    }

    public String getLocation() {
        return waitForElementVisible(
            Locators.PropertyDetails.DETAIL_LOCATION
        ).getText();
    }

    public String getType() {
        try {
            return getText(Locators.PropertyDetails.DETAIL_TYPE);
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public List<WebElement> getSpecs() {
        return waitForElementsPresent(Locators.PropertyDetails.DETAIL_SPECS);
    }

    public String getDescription() {
        return waitForElementVisible(
            Locators.PropertyDetails.DETAIL_DESCRIPTION
        ).getText();
    }

    public boolean isHostAvatarDisplayed() {
        return isDisplayed(Locators.PropertyDetails.HOST_AVATAR);
    }

    public String getHostName() {
        return waitForElementVisible(
            Locators.PropertyDetails.HOST_NAME
        ).getText();
    }

    public String getHostSince() {
        return waitForElementVisible(
            Locators.PropertyDetails.HOST_SINCE
        ).getText();
    }

    public List<WebElement> getAmenities() {
        return waitForElementsPresent(Locators.PropertyDetails.AMENITY_ITEMS);
    }

    public boolean isShowAllAmenitiesButtonDisplayed() {
        return isDisplayed(Locators.PropertyDetails.SHOW_ALL_AMENITIES_BUTTON);
    }

    public String getPrice() {
        return waitForElementVisible(
            Locators.PropertyDetails.PRICE_AMOUNT
        ).getText();
    }

    public boolean isReviewsSectionPresent() {
        return !driver
            .findElements(Locators.PropertyDetails.REVIEWS_SECTION)
            .isEmpty();
    }

    public boolean isPropertyNotFoundDisplayed() {
        try {
            WebElement error = waitForElementVisible(
                Locators.PropertyDetails.AUTH_ERROR
            );
            return error.getText().toLowerCase().contains("property not found");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<String> getSpecTexts() {
        return getSpecs()
            .stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    public List<String> getAmenityTexts() {
        return getAmenities()
            .stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    public int getDisplayedAmenityCount() {
        return driver
            .findElements(Locators.PropertyDetails.AMENITY_ITEMS)
            .size();
    }

    public boolean isAmenitiesSectionPresent() {
        return !driver
            .findElements(Locators.PropertyDetails.AMENITIES_SECTION)
            .isEmpty();
    }

    public boolean amenitiesHaveIconAndLabel() {
        List<WebElement> items = driver.findElements(
            Locators.PropertyDetails.AMENITY_ITEMS
        );
        if (items.isEmpty()) return false;
        return items
            .stream()
            .allMatch(item -> {
                List<WebElement> spans = item.findElements(By.tagName("span"));
                return (
                    spans.size() >= 2 &&
                    !spans.get(0).getText().isBlank() &&
                    !spans.get(1).getText().isBlank()
                );
            });
    }

    public List<WebElement> getGalleryImages() {
        return waitForElementsPresent(Locators.PropertyDetails.GALLERY_IMAGES);
    }

    public void clickWishlistButton() {
        waitForElementClickable(
            Locators.PropertyDetails.DETAIL_WISHLIST_BTN
        ).click();
    }

    public boolean isFavorite() {
        return !driver
            .findElements(Locators.PropertyDetails.DETAIL_WISHLIST_FAV_BTN)
            .isEmpty();
    }

    public void waitForFavorite() {
        wait.until(d ->
            !d
                .findElements(Locators.PropertyDetails.DETAIL_WISHLIST_FAV_BTN)
                .isEmpty()
        );
    }

    public void waitForWishlistUnfavorited() {
        wait.until(d ->
            d
                .findElements(Locators.PropertyDetails.DETAIL_WISHLIST_FAV_BTN)
                .isEmpty()
        );
    }

    // ── Booking Widget ────────────────────────────────────────────────────────

    public boolean isBookingWidgetDisplayed() {
        return isDisplayed(Locators.BookingWidget.CONTAINER);
    }

    public boolean isCheckInDatePickerDisplayed() {
        return isDisplayed(Locators.BookingWidget.CHECK_IN_BTN);
    }

    public boolean isCheckOutDatePickerDisplayed() {
        return isDisplayed(Locators.BookingWidget.CHECK_OUT_BTN);
    }

    public boolean isGuestSelectorDisplayed() {
        return isDisplayed(Locators.BookingWidget.GUESTS_SECTION);
    }

    public boolean isReserveButtonDisplayed() {
        return isDisplayed(Locators.BookingWidget.RESERVE_BTN);
    }


    public void openCheckInDatePicker() {
        waitForElementClickable(Locators.BookingWidget.CHECK_IN_BTN).click();
        waitForElementVisible(Locators.BookingWidget.DATE_PICKER_OVERLAY);
    }

    public void selectNthAvailableDate(int index) {
        List<WebElement> days = waitForElementsPresent(
            Locators.BookingWidget.DATE_PICKER_AVAILABLE_DAYS
        );
        days.get(index).click();
    }

    public void waitForDatePickerToClose() {
        wait.until(d ->
            d.findElements(Locators.BookingWidget.DATE_PICKER_OVERLAY).isEmpty()
        );
    }

    public void closeDatePicker() {
        waitForElementClickable(
            Locators.BookingWidget.DATE_PICKER_CLOSE_BTN
        ).click();
        waitForDatePickerToClose();
    }


    public void clickReserveButton() {
        waitForElementClickable(Locators.BookingWidget.RESERVE_BTN).click();
    }

    public boolean isBookingErrorDisplayed() {
        try {
            return waitForElementVisible(Locators.BookingWidget.ERROR).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPriceBreakdownDisplayed() {
        return isDisplayed(Locators.BookingWidget.BREAKDOWN);
    }

    public String getPriceBreakdownText() {
        return waitForElementVisible(
            Locators.BookingWidget.BREAKDOWN
        ).getText();
    }

    public int getGuestCount() {
        return Integer.parseInt(
            waitForElementVisible(Locators.BookingWidget.GUESTS_VALUE)
                .getText()
                .trim()
        );
    }

    public void clickIncrementGuests() {
        waitForElementClickable(
            Locators.BookingWidget.GUESTS_INCREMENT
        ).click();
    }

    public boolean isIncrementButtonDisabled() {
        WebElement btn = waitForElementVisible(
            Locators.BookingWidget.GUESTS_INCREMENT
        );
        return !btn.isEnabled() || btn.getAttribute("disabled") != null;
    }

    public boolean isDecrementButtonDisabled() {
        WebElement btn = waitForElementVisible(
            Locators.BookingWidget.GUESTS_DECREMENT
        );
        return !btn.isEnabled() || btn.getAttribute("disabled") != null;
    }

    public void incrementGuestsTo(int target) {
        int current = getGuestCount();
        for (int i = current; i < target; i++) {
            waitForElementClickable(
                Locators.BookingWidget.GUESTS_INCREMENT
            ).click();
        }
    }

    // ── Availability API ──────────────────────────────────────────────────────

    public WebElement getCalendarDateElement(LocalDate date) {
        String monthYear = date.format(TestDataConstants.MONTH_YEAR_FORMATTER);
        String day = String.valueOf(date.getDayOfMonth());

        String xpath = String.format(
                "//div[contains(@class, 'date-picker-month')][.//h3[normalize-space()='%s']]//button[contains(@class, 'date-picker-day')][normalize-space()='%s']",
                monthYear,
                day
        );
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public List<WebElement> getCalendarDatesElements(List<LocalDate> dates) {
        openCheckInDatePicker();
        return dates.stream()
                .map(this::getCalendarDateElement)
                .collect(Collectors.toList());
    }

    public boolean isDateDisabled(WebElement dateElement) {
        return !dateElement.isEnabled();
    }

    public boolean areDatesDisabled(List<LocalDate> dates) {
        List<WebElement> dateElements = getCalendarDatesElements(dates);
        return dateElements.stream()
                .allMatch(this::isDateDisabled);
    }

    public List<LocalDate> getAvailableDates(String propertyId) {
        BookingApiPage bookingApiPage = new BookingApiPage(driver);

        Set<LocalDate> booked = new HashSet<>(bookingApiPage.getAllBookedDates(propertyId));

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate endOfNextMonth = LocalDate.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        return tomorrow.datesUntil(endOfNextMonth.plusDays(1))
                .filter(d -> !booked.contains(d))
                .limit(3)
                .toList();
    }

    public boolean areDatesSelectable(List<LocalDate> dates) {
        List<WebElement> dateElements = getCalendarDatesElements(dates);
        return dateElements.stream()
                .allMatch(WebElement::isEnabled);
    }

    // ─────────────────────────────────────────────────────────────────────────

    private void waitForDetailsToLoad() {
        wait.until(
            d ->
                !d
                    .findElements(Locators.PropertyDetails.DETAIL_TITLE)
                    .isEmpty() ||
                !d.findElements(Locators.PropertyDetails.AUTH_ERROR).isEmpty()
        );
    }
}
