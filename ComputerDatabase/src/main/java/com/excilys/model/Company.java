package com.excilys.model;

public class Company {

    private long id;
    private String name = null;

    /* Constructors */

    /**
     * Constructs a new Company.
     * @param id The id of the company
     */
    public Company(long id) {
        this.id = id;
    }

    /**
     * Constructs a new Company.
     * @param id The id of the company
     * @param name The name of the company
     */
    public Company(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /* Getters and Setters */

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /* Object */

    @Override
    public String toString() {
        return "(" + id + ", " + name + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Company other = (Company) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
