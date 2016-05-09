package com.excilys.model;

public enum OrderDirection {

    DEFAULT(""), ASC("asc"), DESC("desc");

    private String value;

    private OrderDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
