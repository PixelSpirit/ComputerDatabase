package com.excilys.controller;

import java.util.regex.Pattern;

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

import com.excilys.dto.DTOComputer;
import com.excilys.mapper.ComputerToDTOComputer;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/computer")
public class ComputerController {

    private static final String PAGE = "page";
    private static final String LIMIT = "limit";
    private static final String SEARCH = "search";
    private static final String ORDERBY = "orderby";
    private static final String DIRECTION = "direction";
    private static final String SELECTION = "selection";
    private static final String EDIT = "edit";

    @Autowired
    private ComputerService computerService;

    @Autowired
    private ComputerListerRequest lister;

    @Autowired
    private ComputerUpdaterRequest updater;

    private Logger logger = LoggerFactory.getLogger(ComputerController.class);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String computerListGetView(@RequestParam(value = PAGE, required = false) String reqPage,
            @RequestParam(value = LIMIT, required = false) String reqLimit,
            @RequestParam(value = SEARCH, required = false) String reqSearch,
            @RequestParam(value = ORDERBY, required = false) String reqOrderby,
            @RequestParam(value = DIRECTION, required = false) String reqDirection, ModelMap model) {
        logger.info("<computer/list> [doGet] received");
        lister.set(reqPage, reqLimit, reqSearch, reqOrderby, reqDirection);
        lister.run(model);
        return "listComputer/main";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String computerRemovePostView(@RequestParam(PAGE) String reqPage, @RequestParam(LIMIT) String reqLimit,
            @RequestParam(value = SEARCH, required = false) String reqSearch,
            @RequestParam(value = ORDERBY, required = false) String reqOrderby,
            @RequestParam(value = DIRECTION, required = false) String reqDirection,
            @RequestParam(SELECTION) String reqSelection, ModelMap model) {
        logger.info("<computer/list> [doPost] received");

        Pattern patternElementPage = Pattern.compile("^\\d+$");
        for (String computerId : reqSelection.split(",")) {
            if (computerId != null && patternElementPage.matcher(computerId).matches()) {
                computerService.remove(Long.parseLong(computerId));
            }
        }

        lister.set(reqPage, reqLimit, reqSearch, reqOrderby, reqDirection);
        lister.run(model);
        return "listComputer/main";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String computerAddGetView(ModelMap model) {
        logger.info("<computer/add> [doGet] received");
        model.addAttribute("dtoComputer", new DTOComputer());
        updater.saveAllCompanies(model);
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
            Computer cpt = updater.getFormComputer(dtoComputer, model);
            computerService.insert(cpt);
            return "redirect:list";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String computerUpdateGetView(@RequestParam(EDIT) long id, ModelMap model) {
        logger.info("<computer/update> [doGet] received");
        DTOComputer computerToEdit = new ComputerToDTOComputer().convert(computerService.find(id));
        model.addAttribute("computerToEdit", computerToEdit);
        updater.saveAllCompanies(model);
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
            Computer cpt = updater.getFormComputer(computerToEdit, model);
            computerService.update(cpt.getId(), cpt);
            return "redirect:list";
        }
    }

}
