package com.excilys.controller;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.excilys.dto.DTOComputer;
import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.OrderBy;
import com.excilys.model.OrderDirection;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.service.ComputerService;

@Component
public class ComputerListerRequest {

    private static final int PAGE_DEFAULT_VALUE = 0;
    private static final int LIMIT_DEFAULT_VALUE = 10;

    String reqPage;
    String reqLimit;
    String reqSearch;
    String reqOrderby;
    String reqDirection;

    @Autowired
    ComputerService computerService;

    public void set(String reqPage, String reqLimit, String reqSearch, String reqOrderby, String reqDirection) {
        this.reqPage = reqPage;
        this.reqLimit = reqLimit;
        this.reqSearch = reqSearch;
        this.reqOrderby = reqOrderby;
        this.reqDirection = reqDirection;
    }

    public void run(ModelMap model) {
        int page = stringToInt(reqPage, PAGE_DEFAULT_VALUE);
        int limit = stringToInt(reqLimit, LIMIT_DEFAULT_VALUE);
        OrderBy orderby = stringToOrderBy(reqOrderby);
        OrderDirection direction = stringToDirection(reqDirection);
        PageRequest pr = new PageRequest(page, limit, reqSearch, orderby, direction);
        Page<DTOComputer> dtoPage = pageToDTO(computerService.findPage(pr));
        long computerNumbers = computerService.count(pr);

        model.addAttribute("page", dtoPage);
        model.addAttribute("computerNumber", computerNumbers);
        if (reqSearch != null) {
            model.addAttribute("search", reqSearch);
        }
        if (orderby != null) {
            model.addAttribute("orderby", orderby.getHttpValue());
        }
        if (direction != null) {
            model.addAttribute("direction", direction.getValue());
        }
    }

    private static int stringToInt(String s, int defaultValue) {
        Pattern patternElementPage = Pattern.compile("^\\d+$");
        if (s != null && patternElementPage.matcher(s).matches()) {
            return Integer.parseInt(s);
        } else {
            return defaultValue;
        }
    }

    private static OrderBy stringToOrderBy(String s) {
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

    private static OrderDirection stringToDirection(String s) {
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
    private static Page<DTOComputer> pageToDTO(Page<Computer> page) {
        DTOComputerMapper mapper = DTOComputerMapper.getInstance();
        Page<DTOComputer> dtoPage = new Page<DTOComputer>(page.getNumber(), page.getMaxNumber(), page.getSize(),
                new ArrayList<>(page.getContent().size()));
        for (Computer computer : page.getContent()) {
            dtoPage.getContent().add(mapper.map(computer));
        }
        return dtoPage;
    }

}
