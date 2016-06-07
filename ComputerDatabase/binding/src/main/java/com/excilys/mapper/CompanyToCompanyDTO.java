package com.excilys.mapper;

import com.excilys.dto.CompanyDTO;
import org.springframework.core.convert.converter.Converter;

import com.excilys.model.Company;

public class CompanyToCompanyDTO implements Converter<Company, CompanyDTO> {

    @Override
    public CompanyDTO convert(Company company) {
        return new CompanyDTO(String.valueOf(company.getId()), company.getName());
    }

}
