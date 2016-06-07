package com.excilys.controller.computer;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerToComputerDTO;
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
    private ComputerService computerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerListerRequest.class);

    public void set(String reqPage, String reqLimit, String reqSearch, String reqOrderby, String reqDirection) {
        this.reqPage = reqPage;
        this.reqLimit = reqLimit;
        this.reqSearch = reqSearch;
        this.reqOrderby = reqOrderby;
        this.reqDirection = reqDirection;
    }

    public void run(ModelMap model) {
        LOGGER.info("Running with " + this);

        int page = stringToInt(reqPage, PAGE_DEFAULT_VALUE);
        int limit = stringToInt(reqLimit, LIMIT_DEFAULT_VALUE);
        if (reqDirection == null) {
            reqDirection = "asc";
        }
        if (reqOrderby == null) {
            reqOrderby = "id";
        }
        Direction direction = Direction.fromString(reqDirection);
        PageRequest pr = new PageRequest(page, limit, direction, reqOrderby);
        Page<ComputerDTO> dtoPage = computerService.findPage(pr).map(new ComputerToComputerDTO());
        long computerNumbers = dtoPage.getTotalElements();

        model.addAttribute("page", dtoPage);
        model.addAttribute("computerNumber", computerNumbers);
        if (reqSearch != null) {
            model.addAttribute("search", reqSearch);
        }
        if (reqOrderby != null) {
            model.addAttribute("orderby", reqOrderby);
        }
        model.addAttribute("direction", direction.toString());
    }

    private static int stringToInt(String s, int defaultValue) {
        Pattern patternElementPage = Pattern.compile("^\\d+$");
        if (s != null && patternElementPage.matcher(s).matches()) {
            return Integer.parseInt(s);
        } else {
            return defaultValue;
        }
    }

    @Override
    public String toString() {
        return "ComputerListerRequest [reqPage=" + reqPage + ", reqLimit=" + reqLimit + ", reqSearch=" + reqSearch
                + ", reqOrderby=" + reqOrderby + ", reqDirection=" + reqDirection + "]";
    }

}
