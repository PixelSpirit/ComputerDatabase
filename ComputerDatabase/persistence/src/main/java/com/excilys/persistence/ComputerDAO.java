package com.excilys.persistence;

import com.excilys.model.Computer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ComputerDAO extends PagingAndSortingRepository<Computer, Long> {

    void deleteByCompany_id(Long company_id);

}