package com.staybnb.data;

public final class PropertyPayloads {
    private PropertyPayloads() {}

    public static String validCreatePropertyPayloadJson() {
        return validCreatePropertyPayloadJson("Automation API Listing");
    }

    public static String validCreatePropertyPayloadJson(String title) {
        return "{"
                + "\"propertyType\":\"ENTIRE_PLACE\","
                + "\"categoryId\":71,"
                + "\"title\":\"" + title + "\","
                + "\"description\":\"Automation flow for create property wizard.\","
                + "\"locationCountry\":\"Afghanistan\","
                + "\"locationCity\":\"Kabul\","
                + "\"locationAddress\":\"Street 1\","
                + "\"maxGuests\":1,"
                + "\"numBedrooms\":1,"
                + "\"numBeds\":1,"
                + "\"numBathrooms\":1,"
                + "\"amenities\":[],"
                + "\"images\":["
                + "{\"url\":\"https://emplavi.com.br/wp-content/uploads/2024/09/HORZON-Fachada-1-Diurna-jpg.webp\",\"caption\":\"\"},"
                + "{\"url\":\"https://is1-2.housingcdn.com/012c1500/96c67b8d4f357e39da3ebbbca1bd60da/v0/medium.jpeg\",\"caption\":\"\"}"
                + "],"
                + "\"pricePerNight\":120"
                + "}";
    }

    public static String invalidCreatePropertyPayloadMissingTitleJson() {
        return "{"
                + "\"propertyType\":\"ENTIRE_PLACE\","
                + "\"categoryId\":71,"
                + "\"title\":\"\","
                + "\"description\":\"Automation flow for create property wizard.\","
                + "\"locationCountry\":\"Afghanistan\","
                + "\"locationCity\":\"Kabul\","
                + "\"locationAddress\":\"Street 1\","
                + "\"maxGuests\":1,"
                + "\"numBedrooms\":1,"
                + "\"numBeds\":1,"
                + "\"numBathrooms\":1,"
                + "\"amenities\":[],"
                + "\"images\":["
                + "{\"url\":\"https://emplavi.com.br/wp-content/uploads/2024/09/HORZON-Fachada-1-Diurna-jpg.webp\",\"caption\":\"\"},"
                + "{\"url\":\"https://is1-2.housingcdn.com/012c1500/96c67b8d4f357e39da3ebbbca1bd60da/v0/medium.jpeg\",\"caption\":\"\"}"
                + "],"
                + "\"pricePerNight\":120"
                + "}";
    }

    public static String validEditPayloadJson() {
        return "{"
                + "\"title\":\"" + Constants.EditProperty.UPDATED_TITLE + "\","
                + "\"description\":\"Automation flow for edit property.\","
                + "\"locationCity\":\"Kabul\","
                + "\"locationCountry\":\"Afghanistan\","
                + "\"pricePerNight\":120"
                + "}";
    }
}
