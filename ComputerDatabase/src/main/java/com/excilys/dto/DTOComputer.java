package com.excilys.dto;

public class DTOComputer {

    private String id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyId;
    private String companyName;

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
        super();
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

}
