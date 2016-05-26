package com.excilys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.dto.DTOCompany;
import com.excilys.dto.DTOComputer;
import com.excilys.mapper.DTOCompanyMapper;
import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/computer")
public class ComputerUpdaterController {

    private static final String EDIT = "edit";

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companiesService;

    private Logger logger = LoggerFactory.getLogger(ComputerUpdaterController.class);

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String computerAddGetView(ModelMap model) {
        logger.info("<computer/add> [doGet] received");
        model.addAttribute("dtoComputer", new DTOComputer());
        saveAllCompanies(model);
        return "addComputer/main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String computerAddPostView(@ModelAttribute("dtoComputer") @Valid DTOComputer dtoComputer,
            BindingResult result, ModelMap model) {
        logger.info("<computer/add> [doPost] received");
        if (result.hasErrors()) {
            for (ObjectError e : result.getAllErrors()) {
                logger.warn(e.toString());
            }
            return "addComputer/main";
        } else {
            logger.info("<computer/add> [Formulaire Submit] Computer To Add : " + dtoComputer);
            Computer cpt = getFormComputer(dtoComputer, model);
            computerService.insert(cpt);
            return "redirect:list";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String computerUpdateGetView(@RequestParam(EDIT) long id, ModelMap model) {
        logger.info("<computer/update> [doGet] received");
        DTOComputer computerToEdit = DTOComputerMapper.getInstance().map(computerService.find(id));
        model.addAttribute("computerToEdit", computerToEdit);
        saveAllCompanies(model);
        return "updateComputer/main";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String computerUpdatePostView(@ModelAttribute("computerToEdit") @Valid DTOComputer computerToEdit,
            BindingResult result, ModelMap model) {
        logger.info("<computer/update> [doPost] received");
        if (result.hasErrors()) {
            for (ObjectError e : result.getAllErrors()) {
                logger.warn(e.toString());
            }
            return "updateComputer/main";
        } else {
            logger.info("<computer/update> [Formulaire Submit] Computer To Update : " + computerToEdit);
            Computer cpt = getFormComputer(computerToEdit, model);
            computerService.update(cpt.getId(), cpt);
            return "redirect:list";
        }
    }

    /**
     * Saves all DTOCompanies reachable from services into the request context.
     * @param model The map model
     * @throws ServiceException if if service is unavailable
     */
    private void saveAllCompanies(ModelMap model) {
        DTOCompanyMapper mapper = DTOCompanyMapper.getInstance();
        List<Company> companies = companiesService.findAll();
        List<DTOCompany> dtoCompanies = new ArrayList<>(companies.size());
        for (Company company : companies) {
            dtoCompanies.add(mapper.map(company));
        }
        model.addAttribute("allCompanies", companiesService.findAll());
    }

    /**
     * Gets computer information from POST request and saves it in the services.
     * @param model The map model
     */
    private Computer getFormComputer(DTOComputer dtoComputer, ModelMap model) {
        if (dtoComputer.getId() == null) {
            dtoComputer.setId("");
        }
        if (!dtoComputer.getCompanyId().equals("0")) {
            String companyName = companiesService.find(Long.parseLong(dtoComputer.getCompanyId())).getName();
            dtoComputer.setCompanyName(companyName);
        }
        return DTOComputerMapper.getInstance().unmap(dtoComputer);
    }

}
