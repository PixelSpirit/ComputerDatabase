package com.excilys.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.excilys.model.Company;

public interface CompanyDAO extends PagingAndSortingRepository<Company, Long> {
}