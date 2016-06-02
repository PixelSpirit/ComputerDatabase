package com.excilys.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class DTOComputer {

    private static final String DATE_REGEXP = "(^[0-9]{4}-(0[1-9]|1[012])-([0-2][0-9]|3[0-1])$)|(^$)";
    private static final String INT_REGEXP = "^\\d+$";

    private String id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Pattern(regexp = DATE_REGEXP)
    private String introduced;

    @NotNull
    @Pattern(regexp = DATE_REGEXP)
    private String discontinued;

    @NotNull
    @Pattern(regexp = INT_REGEXP)
    private String companyId;

    private String companyName;

    public DTOComputer() {
    }

    /**
     * Constructs a DTOComputer.
     * @param id The id
     * @param name The name
     * @param introduced The introduced date
     * @param discontinued The discontinued date
     * @param companyId The company id
     * @param companyName The company name
     */
    public DTOComputer(String id, String name, String introduced, String discontinued, String companyId,
            String companyName) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyId = companyId;
        this.companyName = companyName;
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

    /**
     * @return the introduced
     */
    public String getIntroduced() {
        return introduced;
    }

    /**
     * @return the discontinued
     */
    public String getDiscontinued() {
        return discontinued;
    }

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param introduced the introduced to set
     */
    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    /**
     * @param discontinued the discontinued to set
     */
    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DTOComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
                + discontinued + ", companyId=" + companyId + ", companyName=" + companyName + "]";
    }

}
