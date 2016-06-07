package com.excilys.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class PageRequestDTO {

    @NotNull
    @NotEmpty
    String page;

    @NotNull
    @NotEmpty
    String size;

    @NotNull
    String direction;

    @NotNull
    String sortColumn;

    public PageRequestDTO(String page, String size, String direction, String sortColumn) {
        this.page = page;
        this.size = size;
        this.direction = direction;
        this.sortColumn = sortColumn;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }
}
