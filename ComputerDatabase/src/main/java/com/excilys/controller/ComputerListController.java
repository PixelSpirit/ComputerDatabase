package com.excilys.controller;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.dto.DTOComputer;
import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.OrderBy;
import com.excilys.model.OrderDirection;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/computers")
public class ComputerListController {

    private static final String PAGE = "page";
    private static final String LIMIT = "limit";
    private static final String SEARCH = "search";
    private static final String ORDERBY = "orderby";
    private static final String DIRECTION = "direction";
    private static final String SELECTION = "selection";

    private static final int PAGE_DEFAULT_VALUE = 0;
    private static final int LIMIT_DEFAULT_VALUE = 10;

    @Autowired
    ComputerService computerService;

    private Logger logger = LoggerFactory.getLogger(ComputersServlet.class);

    @RequestMapping(method = RequestMethod.GET)
    public String computerListGetView(@RequestParam(value = PAGE, required = false) String reqPage,
            @RequestParam(value = LIMIT, required = false) String reqLimit,
            @RequestParam(value = SEARCH, required = false) String reqSearch,
            @RequestParam(value = ORDERBY, required = false) String reqOrderby,
            @RequestParam(value = DIRECTION, required = false) String reqDirection, ModelMap model) {

        logger.info("<ComputersServlet> [doGet] received");
        addAttributes(reqPage, reqLimit, reqSearch, reqOrderby, reqDirection, model);
        return "computers/computers";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String computerListPsotView(@RequestParam(PAGE) String reqPage, @RequestParam(LIMIT) String reqLimit,
            @RequestParam(value = SEARCH, required = false) String reqSearch,
            @RequestParam(value = ORDERBY, required = false) String reqOrderby,
            @RequestParam(value = DIRECTION, required = false) String reqDirection,
            @RequestParam(SELECTION) String reqSelection, ModelMap model) {

        logger.info("<ComputersServlet> [doPost] received");
        removeComputers(reqSelection.split(","));
        addAttributes(reqPage, reqLimit, reqSearch, reqOrderby, reqDirection, model);
        return "computers/computers";
    }

    /* Utils */

    private void addAttributes(String reqPage, String reqLimit, String search, String reqOrderby, String reqDirection,
            ModelMap model) {
        int page = stringToInt(reqPage, PAGE_DEFAULT_VALUE);
        int limit = stringToInt(reqLimit, LIMIT_DEFAULT_VALUE);
        OrderBy orderby = stringToOrderBy(reqOrderby);
        OrderDirection direction = stringToDirection(reqDirection);
        PageRequest pr = new PageRequest(page, limit, search, orderby, direction);
        Page<DTOComputer> dtoPage = pageToDTO(computerService.findPage(pr));
        long computerNumbers = computerService.count(pr);

        model.addAttribute("page", dtoPage);
        model.addAttribute("computerNumber", computerNumbers);
        if (search != null) {
            model.addAttribute("search", search);
        }
        if (orderby != null) {
            model.addAttribute("orderby", orderby.getHttpValue());
        }
        if (direction != null) {
            model.addAttribute("direction", direction.getValue());
        }
    }

    private int stringToInt(String s, int defaultValue) {
        Pattern patternElementPage = Pattern.compile("^\\d+$");
        if (s != null && patternElementPage.matcher(s).matches()) {
            return Integer.parseInt(s);
        } else {
            return defaultValue;
        }
    }

    private OrderBy stringToOrderBy(String s) {
        if (s != null) {
            switch (s) {
            case "name":
                return OrderBy.NAME;
            case "introduced":
                return OrderBy.INTRODUCED;
            case "discontinued":
                return OrderBy.DISCONTINUED;
            case "companyName":
                return OrderBy.COMPANY_NAME;
            default:
                return null;
            }
        } else {
            return null;
        }
    }

    private OrderDirection stringToDirection(String s) {
        if (s != null) {
            switch (s) {
            case "asc":
                return OrderDirection.ASC;
            case "desc":
                return OrderDirection.DESC;
            default:
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Converts a Computer Page to a DTOComputer Page.
     * @param page The page to convert
     * @return the converted page
     */
    private Page<DTOComputer> pageToDTO(Page<Computer> page) {
        DTOComputerMapper mapper = DTOComputerMapper.getInstance();
        Page<DTOComputer> dtoPage = new Page<DTOComputer>(page.getNumber(), page.getMaxNumber(), page.getSize(),
                new ArrayList<>(page.getContent().size()));
        for (Computer computer : page.getContent()) {
            dtoPage.getContent().add(mapper.map(computer));
        }
        return dtoPage;
    }

    /**
     * Remove all computers that match the selection POST parameter.
     * @param request The HTTP request
     */
    private void removeComputers(String[] computers) {
        Pattern patternElementPage = Pattern.compile("^\\d+$");
        for (String computerId : computers) {
            if (computerId != null && patternElementPage.matcher(computerId).matches()) {
                computerService.remove(Long.parseLong(computerId));
            }
        }
    }

}
