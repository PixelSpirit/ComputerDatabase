package com.excilys.mapper;

import org.springframework.core.convert.converter.Converter;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyDTOToCompany implements Converter<CompanyDTO, Company> {

    @Override
    public Company convert(CompanyDTO dto) {
        // Check parseLong
        return new Company(Long.parseLong(dto.getId()), dto.getName());
    }

}
