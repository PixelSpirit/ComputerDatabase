package com.excilys.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;

public enum CompanyValidator {
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(CompanyValidator.class);

    /**
     * Tests if the id is valid.
     * @param id The id to test
     * @throws ValidatorException if the id is not valid
     */
    public void isIdValid(Long id) {
        if (id != null && id <= 0) {
            logger.warn("Invalid id detected : " + id);
            throw new ValidatorException("Id " + id + " is not valid !");
        }
    }

    /**
     * Tests if the name is valid.
     * @param name The name to test
     * @throws ValidatorException if the name is not valid
     */
    public void isNameValid(String name) {
        // TODO : Check pattern
    }

    /**
     * Tests if the company is valid.
     * @param company The company to test
     * @throws ValidatorException if the company is not valid
     */
    public void isCompanyValid(Company company) {
        if (company != null) {
            isIdValid(company.getId());
            isNameValid(company.getName());
        } else {
            logger.warn("null company detected");
            throw new ValidatorException("Company must not be null!");
        }
    }

}
