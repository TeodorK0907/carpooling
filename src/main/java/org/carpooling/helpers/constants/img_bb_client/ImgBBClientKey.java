package org.carpooling.helpers.constants.img_bb_client;

public enum ImgBBClientKey {
    KEY("key"),
    IMAGE("image"),
    STATUS("status"),
    DATA("data"),
    DISPLAY_URL("display_url");

    private final String description;

    ImgBBClientKey(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return description;
    }
}
