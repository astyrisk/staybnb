package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import io.restassured.path.json.JsonPath;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BookingApiPage extends BasePage {
    private static final String AVAILABILITY_API_JS = "com/staybnb/scripts/getPropertyAvailabilityApi.js";

    public BookingApiPage(WebDriver driver) {
        super(driver);
    }

    public List<Map<String, String>> fetchBookedDates(String propertyId) {
        String json = (String) ((JavascriptExecutor) driver).executeAsyncScript(
                loadScript(AVAILABILITY_API_JS),
                AppConstants.SLUG,
                propertyId
        );

        if (json == null || json.isBlank()) {
            return List.of();
        }

        List<Map<String, String>> bookedDates = JsonPath.from(json).getList("bookedDates");
        return bookedDates == null ? List.of() : bookedDates;
    }


    public List<LocalDate> getAllBookedDates(String propertyId) {
        return fetchBookedDates(propertyId).stream()
                .flatMap(booking -> {
                    LocalDate checkIn = LocalDate.parse(booking.get("checkIn"));
                    LocalDate checkOut = LocalDate.parse(booking.get("checkOut"));
                    return checkIn.datesUntil(checkOut);
                }).toList();
    }

}
