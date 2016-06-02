package com.excilys.mapper;

import org.springframework.core.convert.converter.Converter;

import com.excilys.dto.DTOCompany;
import com.excilys.model.Company;

public class CompanyToDTOCompany implements Converter<Company, DTOCompany> {

    @Override
    public DTOCompany convert(Company company) {
        return new DTOCompany(String.valueOf(company.getId()), company.getName());
    }

}
