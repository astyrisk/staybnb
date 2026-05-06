package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.model.Notification;
import io.restassured.path.json.JsonPath;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BookingApiPage extends BasePage {
    private static final String AVAILABILITY_API_JS = "com/staybnb/scripts/getPropertyAvailabilityApi.js";
    private static final String NOTIFICATIONS_API_JS = "com/staybnb/scripts/getNotifications.js";

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

    public List<Notification> getAllNotifications() {
        String json = (String) ((JavascriptExecutor) driver).executeAsyncScript(
                loadScript(NOTIFICATIONS_API_JS),
                AppConstants.SLUG
        );

        if (json == null || json.isBlank()) {
            return List.of();
        }

        List<Map<String, Object>> raw = JsonPath.from(json).getList("notifications");
        if (raw == null) return List.of();

        return raw.stream()
                .map(m -> new Notification(
                        ((Number) m.get("id")).intValue(),
                        ((Number) m.get("tenant_id")).intValue(),
                        ((Number) m.get("user_id")).intValue(),
                        (String) m.get("type"),
                        (String) m.get("title"),
                        (String) m.get("message"),
                        (String) m.get("reference_type"),
                        ((Number) m.get("reference_id")).intValue(),
                        (Boolean) m.get("is_read"),
                        (String) m.get("created_at")
                ))
                .toList();
    }

    public List<Notification> getActiveBookingNotifications() {
        return getAllNotifications().stream()
                .filter(n -> !n.type().equals("BOOKING_CANCELLED"))
                .toList();
    }
}
