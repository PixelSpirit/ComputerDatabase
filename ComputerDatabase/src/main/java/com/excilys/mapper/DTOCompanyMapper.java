package com.excilys.mapper;

import com.excilys.dto.DTOCompany;
import com.excilys.model.Company;

public class DTOCompanyMapper implements Mappable<Company, DTOCompany> {

    /* Singleton */

    private static DTOCompanyMapper instance = null;

    /**
     * Constructs a CompanyMapper.
     */
    private DTOCompanyMapper() {
        super();
    }

    /**
     * @return The unique instance of a CompanyMapper.
     */
    public static DTOCompanyMapper getInstance() {
        if (instance == null) {
            synchronized (DTOCompanyMapper.class) {
                if (instance == null) {
                    instance = new DTOCompanyMapper();
                }
            }
        }
        return instance;
    }

    /* Mapper */

    @Override
    public DTOCompany map(Company object) {
        return new DTOCompany(String.valueOf(object.getId()), object.getName());
    }

    @Override
    public Company unmap(DTOCompany object) {
        // Check parseLong
        return new Company(Long.parseLong(object.getId()), object.getName());
    }

}
