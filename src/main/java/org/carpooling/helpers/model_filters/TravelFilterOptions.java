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
    private Optional<Integer> pageNum;
    private Optional<Integer> pageSize;
    private Optional<String> sortBy;
    private Optional<String> orderBy;

    public TravelFilterOptions(String startLocation,
                               String endLocation,
                               String driver,
                               LocalDateTime departAfter,
                               LocalDateTime departBefore,
                               Integer freeSpots,
                               Integer pageNum,
                               Integer pageSize,
                               String sortBy,
                               String orderBy) {
        this.startLocation = Optional.ofNullable(startLocation);
        this.endLocation = Optional.ofNullable(endLocation);
        this.driver = Optional.ofNullable(driver);
        this.departAfter = Optional.ofNullable(departAfter);
        this.departBefore = Optional.ofNullable(departBefore);
        this.freeSpots = Optional.ofNullable(freeSpots);
        this.pageNum = Optional.ofNullable(pageNum);
        this.pageSize = Optional.ofNullable(pageSize);
        this.sortBy = Optional.ofNullable(sortBy);
        this.orderBy = Optional.ofNullable(orderBy);
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

    public Optional<Integer> getPageNum() {
        return pageNum;
    }

    public void setPageNum(Optional<Integer> pageNum) {
        this.pageNum = pageNum;
    }

    public Optional<Integer> getPageSize() {
        return pageSize;
    }

    public void setPageSize(Optional<Integer> pageSize) {
        this.pageSize = pageSize;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(Optional<String> sortBy) {
        this.sortBy = sortBy;
    }

    public Optional<String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Optional<String> orderBy) {
        this.orderBy = orderBy;
    }
}