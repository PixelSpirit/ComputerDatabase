package com.excilys.mapper;

import com.excilys.dto.ComputerDTO;
import org.springframework.core.convert.converter.Converter;

import com.excilys.model.Computer;

public class ComputerToComputerDTO implements Converter<Computer, ComputerDTO> {

    @Override
    public ComputerDTO convert(Computer computer) {
        String id = String.valueOf(computer.getId());
        String introduced = (computer.getIntroduced() != null) ? computer.getIntroduced().toString() : "";
        String discontinued = (computer.getDiscontinued() != null) ? computer.getDiscontinued().toString() : "";
        String companyId = "";
        String companyName = "";
        if (computer.getCompany() != null) {
            companyId = String.valueOf(computer.getCompany().getId());
            companyName = computer.getCompany().getName();
        }
        return new ComputerDTO(id, computer.getName(), introduced, discontinued, companyId, companyName);
    }

}
