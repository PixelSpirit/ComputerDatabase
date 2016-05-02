package com.excilys.dto;

public class DTOCompany {

    private long id;
    private String name;

    /**
     * Constructs a DTOCompany.
     * @param id The id
     * @param name The name
     */
    public DTOCompany(long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

}
