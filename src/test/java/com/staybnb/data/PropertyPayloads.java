package com.staybnb.data;

import com.staybnb.config.TestDataConstants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class PropertyPayloads {
    private static final String CREATE_TEMPLATE = "payloads/create-property.json";
    private static final String EDIT_TEMPLATE   = "payloads/edit-property.json";

    private PropertyPayloads() {}

    public static String validCreatePropertyPayloadJson() {
        return withTitle(CREATE_TEMPLATE, "Automation API Listing");
    }

    public static String validCreatePropertyPayloadJson(String title) {
        return withTitle(CREATE_TEMPLATE, title);
    }

    public static String invalidCreatePropertyPayloadMissingTitleJson() {
        return withTitle(CREATE_TEMPLATE, "");
    }

    public static String validEditPayloadJson() {
        return withTitle(EDIT_TEMPLATE, TestDataConstants.EditProperty.UPDATED_TITLE);
    }

    private static String withTitle(String resourcePath, String title) {
        try (InputStream stream = PropertyPayloads.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalStateException("Missing payload template on classpath: " + resourcePath);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("{{title}}", title);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load payload template: " + resourcePath, e);
        }
    }
}
