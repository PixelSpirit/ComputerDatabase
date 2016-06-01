package com.excilys.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.excilys.dto.DTOComputer;
import com.excilys.mapper.ComputerToDTOComputer;
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
        Direction direction = Direction.fromString(reqDirection);
        PageRequest pr = new PageRequest(page, limit, direction, reqOrderby);
        Page<DTOComputer> dtoPage = computerService.findPage(pr).map(new ComputerToDTOComputer());
        long computerNumbers = dtoPage.getTotalElements();

        model.addAttribute("page", dtoPage);
        model.addAttribute("computerNumber", computerNumbers);
        if (reqSearch != null) {
            model.addAttribute("search", reqSearch);
        }
        if (reqOrderby != null) {
            model.addAttribute("orderby", reqOrderby);
        }
        if (direction != null) {
            model.addAttribute("direction", direction.toString());
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

}
