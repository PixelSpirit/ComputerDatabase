package com.excilys.service;

import static org.junit.Assert.assertEquals;

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

    public void compareComputers(Computer c1, Computer c2) {
        assertEquals(c1.getName(), c2.getName());
        assertEquals(c1.getIntroduced(), c2.getIntroduced());
        assertEquals(c1.getDiscontinued(), c2.getDiscontinued());
        assertEquals(c1.getCompany(), c2.getCompany());
    }

    @BeforeClass
    public static void InitialiseDB() throws IOException, InterruptedException {
        DBInitialiser.run();
    }

    @Test
    public void findTest() {
        // | 78 | Macintosh 512K | 1984-09-10 00:00:00 | 1986-04-14 00:00:00 | 1

        Computer expected = new Computer.Builder("Macintosh 512K").id(78L).introduced(LocalDate.of(1984, 9, 10))
                .discontinued(LocalDate.of(1986, 4, 14)).company(new Company(1, "Apple Inc.")).build();
        Computer actual = service.find(78);
        assertEquals(expected, actual);
    }

    @Test
    @Ignore
    public void findAllTest() {
        throw new NotYetImplementedException();
    }

    @Test
    public void findPageTest() {
        // | id | name | introduced | discontinued | company_id |
        //
        // | 10 | Apple IIc Plus | NULL | NULL | NULL |
        // | 11 | Apple II Plus | NULL | NULL | NULL |
        // | 12 | Apple III | 1980-05-01 00:00:00 | 1984-04-01 00:00:00 | 1 |

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
        expected.setId(575L);
        assertEquals(expected, actual);
    }

    @Test
    public void updateTest() {
        throw new NotYetImplementedException();
    }

    @Test
    public void countTest() {
        throw new NotYetImplementedException();
    }

    @Test
    public void count2Test() {
        throw new NotYetImplementedException();
    }

    @Test
    public void removeTest() {
        throw new NotYetImplementedException();
    }

    // @Test
    // public void insertTest() {
    // Computer c = service.insert(cpt);
    // compareComputers(cpt, c);
    // }
    //
    // @Test
    // public void findTest() {
    // Computer c1 = service.insert(cpt);
    // Computer c2 = service.find(c1.getId());
    // compareComputers(c1, c2);
    // }
    //
    // @Test(expected = NotFoundException.class)
    // public void removeTest() {
    // Computer c = service.insert(cpt);
    // service.remove(c.getId());
    // service.find(c.getId());
    // }
    //
    // @Test
    // public void updateTest() {
    // Computer cpt2 = new
    // Computer.Builder("NewOne").introduced(LocalDate.of(2017, 1, 1))
    // .discontinued(LocalDate.of(2018, 1, 1)).build();
    // Computer c = service.insert(cpt);
    // service.update(c.getId(), cpt2);
    // Computer res = service.find(c.getId());
    // compareComputers(res, cpt2);
    // }

}
