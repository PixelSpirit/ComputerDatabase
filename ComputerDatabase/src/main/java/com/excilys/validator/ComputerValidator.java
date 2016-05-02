package com.excilys.validator;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public enum ComputerValidator {
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

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
     * Tests if the date is valid.
     * @param date The date to test
     * @throws ValidatorException if the date is not valid
     */
    public void isDateValid(LocalDate date) {
        if (date != null && (date.isBefore(LocalDate.of(1970, 1, 1)) || date.isAfter(LocalDate.of(2038, 1, 19)))) {
            logger.warn("Invalid date detected : " + date);
            throw new ValidatorException("Date " + date + " is not valid !");
        }
    }

    /**
     * Tests if date1 is before date2.
     * @param date1 The first date
     * @param date2 The second date
     * @throws ValidatorException if the date order is not valid
     */
    public void isDateOrderValid(LocalDate date1, LocalDate date2) {
        if (date1 != null && date2 != null && date1.isAfter(date2)) {
            logger.warn("Invalid date order detected : " + date1 + ", " + date2);
            throw new ValidatorException("Date " + date1 + " must be before date " + date2 + " !");
        }
    }

    /**
     * Tests if the computer's company is valid.
     * @param company The company to test
     * @throws ValidatorException if the company is not valid
     */
    public void isCompanyValid(Company company) {
        if (company != null) {
            CompanyValidator.INSTANCE.isCompanyValid(company);
        }
    }

    /**
     * Tests if a computer is valid.
     * @param computer The computer to test
     * @throws ValidatorException if the computer is not valid or null
     */
    public void idComputerValid(Computer computer) {
        if (computer != null) {
            isDateValid(computer.getIntroduced());
            isNameValid(computer.getName());
            isDateValid(computer.getDiscontinued());
            isDateOrderValid(computer.getIntroduced(), computer.getDiscontinued());
            isCompanyValid(computer.getCompany());
        } else {
            logger.warn("null computer detected");
            throw new ValidatorException("Computer must not be null!");
        }
    }

}
