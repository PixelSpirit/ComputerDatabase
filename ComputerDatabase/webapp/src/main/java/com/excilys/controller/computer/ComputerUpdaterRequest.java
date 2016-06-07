package com.excilys.controller.computer;

import java.util.ArrayList;
import java.util.List;

import com.excilys.dto.ComputerDTO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.CompanyToCompanyDTO;
import com.excilys.mapper.ComputerDTOToComputer;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;

@Component
public class ComputerUpdaterRequest {

    @Autowired
    private CompanyService companyService;

    /**
     * Saves all DTOCompanies reachable from services into the request context.
     * @param model The map model
     * @throws ServiceException if if service is unavailable
     */
    public void saveAllCompanies(ModelMap model) {
        Iterable<Company> companies = companyService.findAll();
        List<CompanyDTO> dtoCompanies = new ArrayList<>();
        for (Company company : companies) {
            dtoCompanies.add(new CompanyToCompanyDTO().convert(company));
        }
        model.addAttribute("allCompanies", companyService.findAll());
    }

    /**
     * Gets computer information from POST request and saves it in the services.
     * @param model The map model
     */
    public Computer getFormComputer(ComputerDTO dtoComputer, ModelMap model) {
        if (dtoComputer.getId() == null) {
            dtoComputer.setId("");
        }
        if (!dtoComputer.getCompanyId().equals("0")) {
            String companyName = companyService.find(Long.parseLong(dtoComputer.getCompanyId())).getName();
            dtoComputer.setCompanyName(companyName);
        }
        return new ComputerDTOToComputer().convert(dtoComputer);
    }
}
