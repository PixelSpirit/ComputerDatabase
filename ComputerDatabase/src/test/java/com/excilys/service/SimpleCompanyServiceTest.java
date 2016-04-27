package com.excilys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.NotFoundException;

public class SimpleCompanyServiceTest {

    private static SimpleServices<Company> service;
    private static Company cpt;
    private static Long id;

    public static void compareCompanies(Company c1, Company c2) {
        assertEquals(c1.getName(), c2.getName());
    }

    @BeforeClass
    public static void initComputer() {
        service = new SimpleServices<>(CompanyDAO.getInstance());
        cpt = Mockito.mock(Company.class);
        when(cpt.getName()).thenReturn("Compuraptor");
        id = null;
    }

    @Before
    public void initId() {
        id = null;
    }

    @After
    public void removeCpt() {
        if (id != null) {
            try {
                service.remove(id);
            } catch (ServiceException e) {
                System.err.println("Remove Fail");
            }
        }
    }

    @Test
    public void insertTest() throws ServiceException {
        Company c = service.insert(cpt);
        compareCompanies(c, cpt);
        id = c.getId();
    }

    @Test
    public void findTest() throws ServiceException, NotFoundException {
        Company c1 = service.insert(cpt);
        Company c2 = service.find(c1.getId());
        compareCompanies(c1, c2);
        id = c1.getId();
    }

    @Test(expected = NotFoundException.class)
    public void removeTest() throws ServiceException, NotFoundException {
        Company c = service.insert(cpt);
        service.remove(c.getId());
        service.find(c.getId());
    }

    @Test
    public void updateTest() throws ServiceException, NotFoundException {
        Company cpt2 = new Company(0, "Hey");
        Company c = service.insert(cpt);
        service.update(c.getId(), cpt2);
        Company res = service.find(c.getId());
        compareCompanies(res, cpt2);
        id = c.getId();
    }

}
