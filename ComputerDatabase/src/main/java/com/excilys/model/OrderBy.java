package com.excilys.model;

public enum OrderBy {

    DEFAULT("", ""), NAME("cptr.name", "name"), INTRODUCED("cptr.introduced",
            "introduced"), DISCONTINUED("cptr.discontinued", "discontinued"), COMPANY_NAME("cpn.name", "companyName");

    private String sqlColumn;
    private String httpValue;

    private OrderBy(String sqlColumn, String httpValue) {
        this.sqlColumn = sqlColumn;
        this.httpValue = httpValue;
    }

    /**
     * @return the sqlColumn
     */
    public String getSqlColumn() {
        return sqlColumn;
    }

    /**
     * @return the httpValue
     */
    public String getHttpValue() {
        return httpValue;
    }

}
