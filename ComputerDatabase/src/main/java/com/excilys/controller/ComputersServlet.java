package com.excilys.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.DTOComputer;
import com.excilys.model.Page;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.NotFoundException;
import com.excilys.service.ServiceException;
import com.excilys.service.SimpleServices;

/**
 * Servlet implementation class ComputersServlet.
 */
public class ComputersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SimpleServices<Computer> computerService = new SimpleServices<>(ComputerDAO.getInstance());
    private SimpleServices<Company> companyService = new SimpleServices<>(CompanyDAO.getInstance());

    private Logger logger = LoggerFactory.getLogger(ComputersServlet.class);

    /**
     * Saves the computer numbers reachable from services into the request
     * context.
     * @param request The http request
     * @throws ServiceException if if service is unavailable
     */
    private void saveComputerNumbers(HttpServletRequest request) throws ServiceException {
        request.setAttribute("computerNumber", computerService.count());
    }

    /**
     * Converts a Computer Page to a DTOComputer Page.
     * @param page The page to convert
     * @return the converted page
     */
    private Page<DTOComputer> fromComputers(Page<Computer> page) {
        DTOComputerMapper mapper = DTOComputerMapper.getInstance();
        Page<DTOComputer> dtoPage = new Page<DTOComputer>(page.getNumber(), page.getMaxNumber(), page.getSize(),
                new ArrayList<>(page.getContent().size()));
        for (Computer computer : page.getContent()) {
            dtoPage.getContent().add(mapper.map(computer));
        }
        return dtoPage;
    }

    /**
     * Gets pages information from the GET request and saves it in the request
     * context.
     * @param request The http request
     * @throws ServiceException if service is unavailable
     */
    private void savePage(HttpServletRequest request) throws ServiceException {
        try {
            int number = Integer.parseInt(request.getParameter("page"));
            int size = Integer.parseInt(request.getParameter("limit"));
            Page<DTOComputer> dtoPage = fromComputers(computerService.findPage(number, size));
            request.setAttribute("page", dtoPage);
        } catch (NullPointerException | NumberFormatException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getStackTrace()[0].toString());
            Page<DTOComputer> dtoPage = fromComputers(computerService.findPage(0, 10));
            request.setAttribute("page", dtoPage);
        }
    }

    /**
     * Gets computer information from POST request and saves it in the services.
     * @param request The http request
     */
    private void insertComputer(HttpServletRequest request) {
        String name = request.getParameter("name");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyId = request.getParameter("companyid");
        String companyName = "";
        try {
            companyName = companyService.find(Long.parseLong(companyId)).getName();
        } catch (NumberFormatException | NotFoundException | ServiceException e) {
            companyId = "";
            logger.warn("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
        }
        DTOComputer dtoCpt = new DTOComputer(0, name, introduced, discontinued, companyId, companyName);
        Computer cpt = DTOComputerMapper.getInstance().unmap(dtoCpt);
        try {
            computerService.insert(cpt);
        } catch (ServiceException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            System.err.println("Computer was not added");
        }
    }

    /**
     * Loads the jsp file that handles computers pages.
     * @param request The http requestif the request for the GET could not be
     *        handled
     * @param response The http response
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException if an input or output error is detected when the
     *         servlet handles the GET request
     */
    public void laodJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            saveComputerNumbers(request);
            savePage(request);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/computers/computers.jsp").forward(request,
                    response);
        } catch (ServiceException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/404.html").forward(request, response);
        }
    }

    /* Servlet */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("ComputersServlet : [doGet]");
        laodJsp(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("ComputersServlet : [doPost]");
        insertComputer(request);
        laodJsp(request, response);
    }
}
