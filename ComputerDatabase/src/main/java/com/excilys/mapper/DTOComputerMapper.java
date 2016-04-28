package com.excilys.mapper;

import java.time.LocalDateTime;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.DTOComputer;

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
        Computer.Builder builder = new Computer.Builder();
        builder = builder.id(object.getId()).name(object.getName());
        if (object.getIntroduced().equals("")) {
            builder.introduced(
                    LocalDateTime.parse(object.getIntroduced() + " 00.00.0", Computer.formatter).toLocalDate());
        }
        if (object.getDiscontinued().equals("")) {
            builder.discontinued(
                    LocalDateTime.parse(object.getDiscontinued() + " 00.00.0", Computer.formatter).toLocalDate());
        }
        if (object.getCompanyId().equals("") || object.getCompanyName().equals("")) {
            builder.company(new Company(Long.parseLong(object.getCompanyId()), object.getCompanyName()));
        }
        return builder.build();
    }

}
