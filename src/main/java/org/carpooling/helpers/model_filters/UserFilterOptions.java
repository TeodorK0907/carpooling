package org.carpooling.helpers.model_filters;

import java.util.Optional;

public class UserFilterOptions {
    private Optional<String> username;
    private Optional<String> email;
    private Optional<String> phoneNumber;
    private Optional<Integer> pageNum;
    private Optional<Integer> pageSize;
    private Optional<String> sortBy;
    private Optional<String> orderBy;

    public UserFilterOptions(String username,
                             String email,
                             String firstName,
                             Integer pageNum,
                             Integer pageSize,
                             String sortBy,
                             String orderBy) {
        this.username = Optional.ofNullable(username);
        this.email = Optional.ofNullable(email);
        this.phoneNumber = Optional.ofNullable(firstName);
        this.pageNum = Optional.ofNullable(pageNum);
        this.pageSize = Optional.ofNullable(pageSize);
        this.sortBy = Optional.ofNullable(sortBy);
        this.orderBy = Optional.ofNullable(orderBy);
    }

    public Optional<String> getUsername() {
        return username;
    }

    public void setUsername(Optional<String> username) {
        this.username = username;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Optional<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
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
