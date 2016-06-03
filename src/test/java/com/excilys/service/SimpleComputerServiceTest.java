package com.excilys.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.utils.DBInitialiser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class SimpleComputerServiceTest {

    @Autowired
    private ComputerService service;

    @BeforeClass
    public static void InitialiseDB() throws IOException, InterruptedException {
        DBInitialiser.run();
    }

    @Test
    public void findTest() {
        Computer expected = new Computer.Builder("Macintosh 512K").id(78L).introduced(LocalDate.of(1984, 9, 10))
                .discontinued(LocalDate.of(1986, 4, 14)).company(new Company(1, "Apple Inc.")).build();
        Computer actual = service.find(78);
        assertEquals(expected, actual);
    }

    @Test
    @Ignore
    public void findAllTest() {
        // To much computers !!!
        throw new NotYetImplementedException();
    }

    @Test
    public void findPageTest() {
        Company company1 = new Company(1, "Apple Inc.");

        List<Computer> expected = new ArrayList<>();
        expected.add(new Computer.Builder("Apple IIc Plus").id(10L).build());
        expected.add(new Computer.Builder("Apple II Plus").id(11L).build());
        expected.add(new Computer.Builder("Apple III").id(12L).introduced(LocalDate.of(1980, 5, 1))
                .discontinued(LocalDate.of(1984, 4, 1)).company(company1).build());

        List<Computer> actual = service.findPage(new PageRequest(3, 3)).getContent();
        assertEquals(expected, actual);
    }

    @Test
    public void insertTest() {
        Computer expected = new Computer.Builder("Gloubiboulga").introduced(LocalDate.of(1984, 12, 3)).build();
        Computer actual = service.insert(expected);
        assertNotNull(actual.getId());
    }

    @Test
    public void updateTest() {
        Company company = new Company(1, "Apple Inc.");
        Computer expectedAtBegining = new Computer.Builder("MacBook Pro 15.4 inch").id(1L).company(company).build();
        Computer actualAtBegining = service.find(1);
        assertEquals(expectedAtBegining, actualAtBegining);
        Computer nowExpected = new Computer.Builder("Nooop Chuck Testa").id(1L).build();
        Computer actual = service.update(1, nowExpected);
        assertEquals(nowExpected, actual);
    }

    @Test
    public void countTest() {
        long nb = service.count();
        assertTrue(nb == 574 || nb == 575);
    }

    @Test
    public void removeTest() {
        service.remove(2);
        Computer c = service.find(2);
        assertNull(c);
    }

}
