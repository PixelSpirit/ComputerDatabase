package com.excilys.dto;

public class CompanyDTO {

    private String id;
    private String name;

    /**
     * Constructs a CompanyDTO.
     * @param id The id
     * @param name The name
     */
    public CompanyDTO(String id, String name) {
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
        return "CompanyDTO [id=" + id + ", name=" + name + "]";
    }

}
