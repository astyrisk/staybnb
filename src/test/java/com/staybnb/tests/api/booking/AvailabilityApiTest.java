package com.staybnb.tests.api.booking;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestConfig;
import com.staybnb.config.TestDataConstants;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Properties")
@Feature("Availability API")
@Tag("api")
public class AvailabilityApiTest extends BaseApiTest {

    @Test
    @DisplayName("Booked property has booked dates")
    public void testBookedPropertyHasBookedDates() {
        List<Map<String, String>> bookedDates = unauthedRequest()
                .get("/properties/" + TestConfig.ONE_BOOKED_PROPERTY_ID + "/availability")
                .jsonPath()
                .getList("bookedDates");

        assertTrue(
                bookedDates.stream().anyMatch(booking ->
                        TestDataConstants.AvailabilityCalendar.BOOKED_CHECK_IN.equals(booking.get("checkIn")) &&
                        TestDataConstants.AvailabilityCalendar.BOOKED_CHECK_OUT.equals(booking.get("checkOut"))
                ),
                ErrorMessages.AVAILABILITY_API_RESPONSE_SHOULD_CONTAIN_BOOKED_DATES
        );
    }

    @Test
    @DisplayName("Unbooked property has empty booked dates")
    public void testUnbookedPropertyHasEmptyBookedDates() {
        List<Map<String, String>> bookedDates = unauthedRequest()
                .get("/properties/" + TestConfig.ZERO_BOOKED_PROPERTY_ID + "/availability")
                .jsonPath()
                .getList("bookedDates");

        assertTrue(
                bookedDates == null || bookedDates.isEmpty(),
                ErrorMessages.AVAILABILITY_API_SHOULD_RETURN_EMPTY_FOR_UNBOOKED_PROPERTY
        );
    }
}
