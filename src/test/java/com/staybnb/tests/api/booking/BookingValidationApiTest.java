package com.staybnb.tests.api.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestConfig;
import com.staybnb.config.TestDataConstants;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

//TODO needs to be reviewed

@Epic("Bookings")
@Feature("Booking Validation")
@Tag("api")
public class BookingValidationApiTest extends BaseApiTest {

    // ── AC1 — Invalid date range ──────────────────────────────────────────────

    @ParameterizedTest(name = "checkIn={0} checkOut={1} returns 400")
    @MethodSource("invalidDateRanges")
    @DisplayName("Booking with invalid date range returns 400")
    public void testInvalidDateRangeReturns400(String checkIn, String checkOut) {
        long status = loggedInRequest()
                .contentType(ContentType.JSON)
                .body(buildPayload(TestConfig.TO_BOOK_PROPERTY_ID, checkIn, checkOut, TestDataConstants.Booking.NUM_GUESTS))
                .post("/bookings")
                .statusCode();

        assertEquals(400L, status, ErrorMessages.BOOKING_INVALID_DATE_RANGE_SHOULD_RETURN_400);
    }

    private static Stream<Arguments> invalidDateRanges() {
        return Stream.of(
            Arguments.of("2026-06-01", "2026-06-01"),   // checkOut == checkIn
            Arguments.of("2026-06-01", "2026-05-31")    // checkOut before checkIn
        );
    }

    // ── AC2 — Guest count exceeds property max ────────────────────────────────

    @Test
    @DisplayName("Booking with guest count exceeding property max returns 400")
    public void testGuestCountExceedsMaxReturns400() {
        long status = loggedInRequest()
                .contentType(ContentType.JSON)
                .body(buildPayload(
                        TestConfig.DEFAULT_PROPERTY_ID,
                        TestDataConstants.Booking.VALID_CHECK_IN,
                        TestDataConstants.Booking.VALID_CHECK_OUT,
                        TestDataConstants.Booking.EXCEEDS_MAX_GUESTS))
                .post("/bookings")
                .statusCode();

        assertEquals(400L, status, ErrorMessages.BOOKING_EXCEEDS_MAX_GUESTS_SHOULD_RETURN_400);
    }

    // ── AC3 — Unauthenticated request ─────────────────────────────────────────

    @Test
    @DisplayName("Unauthenticated booking request returns 401")
    public void testUnauthenticatedBookingReturns401() {
        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body(buildPayload(
                        TestConfig.TO_BOOK_PROPERTY_ID,
                        TestDataConstants.Booking.VALID_CHECK_IN,
                        TestDataConstants.Booking.VALID_CHECK_OUT,
                        TestDataConstants.Booking.NUM_GUESTS))
                .post("/bookings")
                .statusCode();

        assertEquals(401L, status, ErrorMessages.BOOKING_WITHOUT_AUTH_SHOULD_RETURN_401);
    }

    // ── AC4 — Missing required fields ─────────────────────────────────────────

    @ParameterizedTest(name = "missing {0} returns 400")
    @MethodSource("missingFieldPayloads")
    @DisplayName("Booking with a missing required field returns 400")
    public void testMissingRequiredFieldReturns400(String missingField, String payload) {
        long status = loggedInRequest()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/bookings")
                .statusCode();

        assertEquals(400L, status, ErrorMessages.BOOKING_MISSING_FIELD_SHOULD_RETURN_400);
    }

    private static Stream<Arguments> missingFieldPayloads() {
        return Stream.of(
            Arguments.of("propertyId", "{\"checkIn\":\"2026-06-01\",\"checkOut\":\"2026-06-05\",\"numGuests\":1}"),
            Arguments.of("checkIn",    "{\"propertyId\":1,\"checkOut\":\"2026-06-05\",\"numGuests\":1}"),
            Arguments.of("checkOut",   "{\"propertyId\":1,\"checkIn\":\"2026-06-01\",\"numGuests\":1}"),
            Arguments.of("numGuests",  "{\"propertyId\":1,\"checkIn\":\"2026-06-01\",\"checkOut\":\"2026-06-05\"}")
        );
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String buildPayload(String propertyId, String checkIn, String checkOut, int numGuests) {
        return String.format(
                "{\"propertyId\":%s,\"checkIn\":\"%s\",\"checkOut\":\"%s\",\"numGuests\":%d}",
                propertyId, checkIn, checkOut, numGuests
        );
    }
}
