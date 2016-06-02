package com.excilys.dto;

public class DTOCompany {

    private String id;
    private String name;

    /**
     * Constructs a DTOCompany.
     * @param id The id
     * @param name The name
     */
    public DTOCompany(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DTOCompany [id=" + id + ", name=" + name + "]";
    }

}
