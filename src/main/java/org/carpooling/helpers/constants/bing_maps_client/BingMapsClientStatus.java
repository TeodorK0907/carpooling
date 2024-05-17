package org.carpooling.helpers.constants.bing_maps_client;
public enum BingMapsClientStatus {
    STATUS_CODE_KEY(200);

    private final Integer code;

    BingMapsClientStatus(Integer description) {
        this.code = description;
    }

    public Integer getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }
}
