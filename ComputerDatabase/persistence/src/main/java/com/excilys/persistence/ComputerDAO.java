package com.excilys.persistence;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.excilys.model.Computer;

@Repository
public interface ComputerDAO extends PagingAndSortingRepository<Computer, Long> {

    public List<Computer> findAllByCompany_id(Long company_id);

    public void deleteByCompany_id(Long company_id);

}