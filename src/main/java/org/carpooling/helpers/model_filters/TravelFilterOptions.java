package org.carpooling.helpers.model_filters;

import java.time.LocalDateTime;
import java.util.Optional;

public class TravelFilterOptions {
    private Optional<String> startLocation;
    private Optional<String> endLocation;
    private Optional<String> driver;
    private Optional<LocalDateTime> departAfter;
    private Optional<LocalDateTime> departBefore;
    private Optional<Integer> freeSpots;

    public TravelFilterOptions(String startLocation,
                               String endLocation,
                               String driver,
                               LocalDateTime departAfter,
                               LocalDateTime departBefore,
                               Integer freeSpots) {
        this.startLocation = Optional.ofNullable(startLocation);
        this.endLocation = Optional.ofNullable(endLocation);
        this.driver = Optional.ofNullable(driver);
        this.departAfter = Optional.ofNullable(departAfter);
        this.departBefore = Optional.ofNullable(departBefore);
        this.freeSpots = Optional.ofNullable(freeSpots);
    }

    public Optional<String> getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Optional<String> startLocation) {
        this.startLocation = startLocation;
    }

    public Optional<String> getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Optional<String> endLocation) {
        this.endLocation = endLocation;
    }

    public Optional<String> getDriver() {
        return driver;
    }

    public void setDriver(Optional<String> driver) {
        this.driver = driver;
    }

    public Optional<LocalDateTime> getDepartAfter() {
        return departAfter;
    }

    public void setDepartAfter(Optional<LocalDateTime> departAfter) {
        this.departAfter = departAfter;
    }

    public Optional<LocalDateTime> getDepartBefore() {
        return departBefore;
    }

    public void setDepartBefore(Optional<LocalDateTime> departBefore) {
        this.departBefore = departBefore;
    }

    public Optional<Integer> getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(Optional<Integer> freeSpots) {
        this.freeSpots = freeSpots;
    }
}
