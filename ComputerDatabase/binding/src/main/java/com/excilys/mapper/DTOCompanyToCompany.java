package com.excilys.mapper;

import org.springframework.core.convert.converter.Converter;

import com.excilys.dto.DTOCompany;
import com.excilys.model.Company;

public class DTOCompanyToCompany implements Converter<DTOCompany, Company> {

    @Override
    public Company convert(DTOCompany dto) {
        // Check parseLong
        return new Company(Long.parseLong(dto.getId()), dto.getName());
    }

}
