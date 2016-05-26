package com.excilys.controller;

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

import com.excilys.dto.DTOComputer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/computer")
public class ComputerUpdaterController {

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companiesService;

    private Logger logger = LoggerFactory.getLogger(ComputerUpdaterController.class);

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String computerAddGetView(ModelMap model) {
        logger.info("<computer/add> [doGet] received");
        model.addAttribute("dtoComputer", new DTOComputer());
        return "addComputer/main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String computerPostView(@ModelAttribute("dtoComputer") @Valid DTOComputer dtoComputer,
            BindingResult result) {
        logger.info("<computer/add> [doPost] received");
        if (result.hasErrors()) {
            for (ObjectError e : result.getAllErrors()) {
                logger.warn(e.toString());
            }
            return "addComputer/main";
        } else {
            logger.info("[Formulaire Submit] Computer To Add : " + dtoComputer);
            return "redirect:list";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String computerUpdateGetView(DTOComputer dto) {
        logger.info("<computer/update> [doGet] received");
        return "updateComputer/main";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String computerUpdatePostView(DTOComputer dto) {
        logger.info("<computer/update> [doPost] received");
        return "updateComputer/main";
    }

}
