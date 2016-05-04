package com.excilys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.DTOComputer;
import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.OrderBy;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.SimpleServices;

/**
 * Servlet implementation class ComputersServlet.
 */
public class ComputersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SimpleServices<Computer> computerService = new SimpleServices<>(ComputerDAO.getInstance());

    private Logger logger = LoggerFactory.getLogger(ComputersServlet.class);

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
            return "";
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
                return OrderBy.DEFAULT;
            }
        } else {
            return OrderBy.DEFAULT;
        }
    }

    private boolean isAscendent(HttpServletRequest request) {
        String param = request.getParameter("isAscendent");
        if (param != null) {
            return param.equals("true");
        } else {
            return true;
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
        boolean isAscendent = isAscendent(request);
        PageRequest pr = new PageRequest(pageNumber, pageSize, search, orderBy, isAscendent);
        Page<DTOComputer> page = fromComputers(computerService.findPage(pr));
        long computerNumbers = computerService.count(pr);

        request.setAttribute("search", search);
        request.setAttribute("orderby", orderBy.getHttpValue());
        request.setAttribute("isAscendent", isAscendent);
        request.setAttribute("page", page);
        request.setAttribute("computerNumber", computerNumbers);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/computers/computers.jsp").forward(request,
                response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("[doGet] received");
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
        logger.info("[doPost] received");
        removeComputers(request);
        runPage(request, response);
    }

}
