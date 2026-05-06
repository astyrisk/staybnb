package com.staybnb.tests.api.booking;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestConfig;
import com.staybnb.config.TestDataConstants;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Bookings")
@Feature("Booking API")
@Tag("api")
public class BookingApiTest extends BaseApiTest {

    private String createdBookingId;

    @AfterEach
    void cancelCreatedBooking() {
        if (createdBookingId != null) {
            loggedInRequest().put("/bookings/" + createdBookingId + "/cancel");
            createdBookingId = null;
        }
    }

    private String buildOverlappingBookingPayload() {
        return String.format(
                "{\"propertyId\":%s,\"checkIn\":\"%s\",\"checkOut\":\"%s\",\"numGuests\":%d}",
                TestConfig.DEFAULT_PROPERTY_ID,
                TestDataConstants.Booking.OVERLAPPING_CHECK_IN,
                TestDataConstants.Booking.OVERLAPPING_CHECK_OUT,
                TestDataConstants.Booking.NUM_GUESTS
        );
    }

    private String buildValidBookingPayload() {
        return String.format(
                "{\"propertyId\":%s,\"checkIn\":\"%s\",\"checkOut\":\"%s\",\"numGuests\":%d}",
                TestConfig.TO_BOOK_PROPERTY_ID,
                TestDataConstants.Booking.VALID_CHECK_IN,
                TestDataConstants.Booking.VALID_CHECK_OUT,
                TestDataConstants.Booking.NUM_GUESTS
        );
    }

    @Test
    @DisplayName("Visitor user Booking overlapping dates returns 401")
    public void testBookingOverlappingDatesReturns409() {
        long status = authedRequest()
                .contentType(ContentType.JSON)
                .body(buildOverlappingBookingPayload())
                .post("/bookings")
                .statusCode();

        assertEquals(
                401L,
                status,
                ErrorMessages.BOOKING_OVERLAPPING_DATES_SHOULD_RETURN_409
        );
    }

    @Test
    @DisplayName("Logged-in user booking overlapping dates returns 409")
    public void testLoggedInUserBookingOverlappingDatesReturns409() {
        long status = loggedInRequest()
                .contentType(ContentType.JSON)
                .body(buildOverlappingBookingPayload())
                .post("/bookings")
                .statusCode();

        assertEquals(
                409L,
                status,
                ErrorMessages.BOOKING_OVERLAPPING_DATES_SHOULD_RETURN_409
        );
    }

    @Test
    @DisplayName("Logged-in user booking valid dates returns PENDING status")
    public void testLoggedInUserBookingValidDatesReturnsPendingStatus() {
        Response response = loggedInRequest()
                .contentType(ContentType.JSON)
                .body(buildValidBookingPayload())
                .post("/bookings");

        createdBookingId = response.jsonPath().getString("id");

        assertEquals(
                TestDataConstants.Booking.EXPECTED_STATUS,
                response.jsonPath().getString("status"),
                ErrorMessages.BOOKING_SHOULD_BE_CREATED_WITH_PENDING_STATUS
        );
    }
}
