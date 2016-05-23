package com.excilys.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class SimpleCompanyServiceTest {

    @Autowired
    private CompanyService service;

    private static Company cpt;

    private static Long id;

    public static void compareCompanies(Company c1, Company c2) {
        assertEquals(c1.getName(), c2.getName());
    }

    @BeforeClass
    public static void initComputer() {
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
            service.remove(id);
        }
    }

    @Test
    public void insertTest() {
        Company c = service.insert(cpt);
        compareCompanies(c, cpt);
        id = c.getId();
    }

    @Test
    public void findTest() {
        Company c1 = service.insert(cpt);
        Company c2 = service.find(c1.getId());
        compareCompanies(c1, c2);
        id = c1.getId();
    }

    @Test
    public void updateTest() {
        Company cpt2 = new Company(0, "Hey");
        Company c = service.insert(cpt);
        service.update(c.getId(), cpt2);
        Company res = service.find(c.getId());
        compareCompanies(res, cpt2);
        id = c.getId();
    }

}
