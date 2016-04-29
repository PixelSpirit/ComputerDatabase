package com.excilys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.NotFoundException;

public class SimpleComputerServiceTest {

    private static SimpleServices<Computer> service;
    private static Computer cpt;
    private static Long id;

    public static void compareComputers(Computer c1, Computer c2) {
        assertEquals(c1.getName(), c2.getName());
        assertEquals(c1.getIntroduced(), c2.getIntroduced());
        assertEquals(c1.getDiscontinued(), c2.getDiscontinued());
        assertEquals(c1.getCompany(), c2.getCompany());
    }

    @BeforeClass
    public static void initComputer() {
        service = new SimpleServices<>(ComputerDAO.getInstance());
        cpt = Mockito.mock(Computer.class);
        when(cpt.getName()).thenReturn("Compuraptor");
        when(cpt.getCompany()).thenReturn(null);
        when(cpt.getIntroduced()).thenReturn(LocalDate.of(2010, 1, 1));
        when(cpt.getDiscontinued()).thenReturn(null);
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
        Computer c = service.insert(cpt);
        compareComputers(c, cpt);
        id = c.getId();
    }

    @Test
    public void findTest() throws ServiceException, NotFoundException {
        Computer c1 = service.insert(cpt);
        Computer c2 = service.find(c1.getId());
        compareComputers(c1, c2);
        id = c1.getId();
    }

    @Test(expected = NotFoundException.class)
    public void removeTest() throws ServiceException, NotFoundException {
        Computer c = service.insert(cpt);
        service.remove(c.getId());
        service.find(c.getId());
    }

    @Test
    public void updateTest() throws ServiceException, NotFoundException {
        Computer cpt2 = new Computer.Builder("NewOne").introduced(LocalDate.of(2017, 1, 1))
                .discontinued(LocalDate.of(2018, 1, 1)).build();
        Computer c = service.insert(cpt);
        service.update(c.getId(), cpt2);
        Computer res = service.find(c.getId());
        compareComputers(res, cpt2);
        id = c.getId();
    }

}
