package com.excilys.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.persistence.AbstractDAO;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ConnectionException;
import com.excilys.persistence.DAOException;

/**
 * Groups the different services to use companies.
 */
public class CompanyServices {

    /**
     * The access to comapanie's DAO.
     */
    private AbstractDAO<Company> dao = CompanyDAO.getInstance();

    private Logger logger = LoggerFactory.getLogger(CompanyServices.class);

    /* Singleton */

    private static CompanyServices instance;

    /**
     * Constructs a CompanyServices.
     */
    private CompanyServices() {
    }

    /**
     * @return The unique instance of a CompanyServices.
     */
    public static CompanyServices getInstance() {
        if (instance == null) {
            synchronized (CompanyServices.class) {
                if (instance == null) {
                    instance = new CompanyServices();
                }
            }
        }
        return instance;
    }

    /* Functionalities */

    /**
     * Prints the size companies from the from-th company found by the DAO.
     *
     * @param from The number of the first company to print
     * @param size The number of companies to print
     * @throws ServiceException If a connection problem or a DAO problem was
     *         encounter
     */
    public void printCompanies(int from, int size) throws ServiceException {
        List<Company> companies;
        try {
            companies = dao.findSeveral(size, from);
            System.out.println("Companies :");

            for (Company company : companies) {
                System.out.println(company);
            }
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            logger.warn("[Throw] <ServiceException>");
            throw new ServiceException(e);
        }
    }

    /**
     * Finds the Company that match with the given id.
     * @param id The id of the Company
     * @return The desired Company
     * @throws ServiceException If a connection problem or a DAO problem was
     *         encounter
     */
    public Company find(long id) throws ServiceException {
        try {
            return dao.find(id);
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            logger.warn("[Throw] <ServiceException>");
            throw new ServiceException(e);
        }
    }
}
