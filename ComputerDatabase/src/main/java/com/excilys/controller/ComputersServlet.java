package com.excilys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.dto.DTOComputer;
import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.OrderBy;
import com.excilys.model.OrderDirection;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.service.ComputerService;

/**
 * Servlet implementation class ComputersServlet.
 */
public class ComputersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ComputerService computerService;

    private Logger logger = LoggerFactory.getLogger(ComputersServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    /**
     * Converts a Computer Page to a DTOComputer Page.
     * @param page The page to convert
     * @return the converted page
     */
    private Page<DTOComputer> fromComputers(Page<Computer> page) {
        // TODO : MOVE IT SOMEWHERE ELSE ! (Nothing to do here)
        DTOComputerMapper mapper = DTOComputerMapper.getInstance();
        Page<DTOComputer> dtoPage = new Page<DTOComputer>(page.getNumber(), page.getMaxNumber(), page.getSize(),
                new ArrayList<>(page.getContent().size()));
        for (Computer computer : page.getContent()) {
            dtoPage.getContent().add(mapper.map(computer));
        }
        return dtoPage;
    }

    private int getNumberParameter(HttpServletRequest request, String s, int defaultValue) {
        String param = request.getParameter(s);
        Pattern patternElementPage = Pattern.compile("^\\d+$");
        if (param != null && patternElementPage.matcher(param).matches()) {
            return Integer.parseInt(param);
        } else {
            return defaultValue;
        }
    }

    private int getPageNumber(HttpServletRequest request) {
        return getNumberParameter(request, "page", 0);
    }

    private int getPageSize(HttpServletRequest request) {
        return getNumberParameter(request, "limit", 10);
    }

    private String getSearch(HttpServletRequest request) {
        String search = request.getParameter("search");
        if (search != null) {
            return search;
        } else {
            return null;
        }
    }

    private OrderBy getOrderBy(HttpServletRequest request) {
        String orderColumn = request.getParameter("orderby");
        if (orderColumn != null) {
            switch (orderColumn) {
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

    private OrderDirection getDirection(HttpServletRequest request) {
        String param = request.getParameter("direction");
        if (param != null) {
            switch (param) {
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
     * Runs the page.
     * @param request The http request
     * @param response The http response
     * @throws ServletException If the servlet encounter a problem
     * @throws IOException If the jsp is not loadable
     */
    private void runPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageNumber = getPageNumber(request);
        int pageSize = getPageSize(request);
        String search = getSearch(request);
        OrderBy orderBy = getOrderBy(request);
        OrderDirection direction = getDirection(request);
        PageRequest pr = new PageRequest(pageNumber, pageSize, search, orderBy, direction);
        Page<DTOComputer> page = fromComputers(computerService.findPage(pr));
        long computerNumbers = computerService.count(pr);

        request.setAttribute("page", page);
        request.setAttribute("computerNumber", computerNumbers);
        if (search != null) {
            request.setAttribute("search", search);
        }
        if (orderBy != null) {
            request.setAttribute("orderby", orderBy.getHttpValue());
        }
        if (direction != null) {
            request.setAttribute("direction", direction.getValue());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/computers/computers.jsp").forward(request,
                response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("<ComputersServlet> [doGet] received");
        runPage(request, response);
    }

    /**
     * Remove all computers that match the selection POST parameter.
     * @param request The HTTP request
     */
    private void removeComputers(HttpServletRequest request) {
        String[] computers = request.getParameter("selection").split(",");
        for (String computerId : computers) {
            // TODO : Verif parseLong !!!
            computerService.remove(Long.parseLong(computerId));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("<ComputersServlet> [doPost] received");
        removeComputers(request);
        runPage(request, response);
    }

}
