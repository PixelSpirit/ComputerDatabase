package com.excilys.mapper;

import org.springframework.core.convert.converter.Converter;

import com.excilys.dto.DTOComputer;
import com.excilys.model.Computer;

public class ComputerToDTOComputer implements Converter<Computer, DTOComputer> {

    @Override
    public DTOComputer convert(Computer computer) {
        String id = String.valueOf(computer.getId());
        String introduced = (computer.getIntroduced() != null) ? computer.getIntroduced().toString() : "";
        String discontinued = (computer.getDiscontinued() != null) ? computer.getDiscontinued().toString() : "";
        String companyId = "";
        String companyName = "";
        if (computer.getCompany() != null) {
            companyId = String.valueOf(computer.getCompany().getId());
            companyName = computer.getCompany().getName();
        }
        return new DTOComputer(id, computer.getName(), introduced, discontinued, companyId, companyName);
    }

}
