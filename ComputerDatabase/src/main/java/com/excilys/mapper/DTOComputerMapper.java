package com.excilys.mapper;

import java.time.LocalDate;

import com.excilys.dto.DTOComputer;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class DTOComputerMapper implements Mappable<Computer, DTOComputer> {

    /* Singleton */

    private static DTOComputerMapper instance = null;

    /**
     * Constructs a CompanyMapper.
     */
    private DTOComputerMapper() {
        super();
    }

    /**
     * @return The unique instance of a CompanyMapper.
     */
    public static DTOComputerMapper getInstance() {
        if (instance == null) {
            synchronized (DTOComputerMapper.class) {
                if (instance == null) {
                    instance = new DTOComputerMapper();
                }
            }
        }
        return instance;
    }

    /* Mapper */

    @Override
    public DTOComputer map(Computer object) {
        String introduced = (object.getIntroduced() != null) ? object.getIntroduced().toString() : "";
        String discontinued = (object.getDiscontinued() != null) ? object.getDiscontinued().toString() : "";
        String companyId = "";
        String companyName = "";
        if (object.getCompany() != null) {
            companyId = String.valueOf(object.getCompany().getId());
            companyName = object.getCompany().getName();
        }
        return new DTOComputer(object.getId(), object.getName(), introduced, discontinued, companyId, companyName);
    }

    @Override
    public Computer unmap(DTOComputer object) {
        Computer.Builder builder = new Computer.Builder(object.getName());
        if (!object.getIntroduced().equals("")) {
            builder.introduced(LocalDate.parse(object.getIntroduced()));
        }
        if (!object.getDiscontinued().equals("")) {
            builder.discontinued(LocalDate.parse(object.getDiscontinued()));
        }
        if (!object.getCompanyId().equals("") && !object.getCompanyName().equals("")) {
            builder.company(new Company(Long.parseLong(object.getCompanyId()), object.getCompanyName()));
        }
        return builder.build();
    }

}
