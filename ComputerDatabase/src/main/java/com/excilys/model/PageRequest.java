package com.excilys.model;

public class PageRequest {

    int pageNumber;
    int pageSize;
    String search;
    OrderBy orderByColumn;
    boolean isAscendent;

    /**
     * Constructs a PageRequest
     * @param pageNumber The page number
     * @param pageSize The page size
     * @param search The search
     * @param likeColumn The like column name
     * @param orderByColumn The order by column name
     * @param isAscendent true if the order of the page is ascendent
     */
    public PageRequest(int pageNumber, int pageSize, String search, OrderBy orderByColumn, boolean isAscendent) {
        super();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.search = search;
        this.orderByColumn = orderByColumn;
        this.isAscendent = isAscendent;
    }

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the search
     */
    public String getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * @return the orderByColumn
     */
    public OrderBy getOrderByColumn() {
        return orderByColumn;
    }

    /**
     * @param orderByColumn the orderByColumn to set
     */
    public void setOrderByColumn(OrderBy orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    /**
     * @return the isAscendent
     */
    public boolean isAscendent() {
        return isAscendent;
    }

    /**
     * @param isAscendent the isAscendent to set
     */
    public void setAscendent(boolean isAscendent) {
        this.isAscendent = isAscendent;
    }

}
