package com.excilys.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.DTOComputer;
import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.Page;
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
     * Saves the computer numbers reachable from services into the request
     * context.
     * @param request The http request
     * @throws ServiceException if if service is unavailable
     */
    private void saveComputerNumbers(HttpServletRequest request) {
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
    private void savePage(HttpServletRequest request) {
        try {
            int number = Integer.parseInt(request.getParameter("page"));
            int size = Integer.parseInt(request.getParameter("limit"));
            Page<DTOComputer> dtoPage = fromComputers(computerService.findPage(number, size));
            request.setAttribute("page", dtoPage);
        } catch (NullPointerException | NumberFormatException e) {
            // TODO : Remove dirty checking
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getStackTrace()[0].toString());
            Page<DTOComputer> dtoPage = fromComputers(computerService.findPage(0, 10));
            request.setAttribute("page", dtoPage);
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
        saveComputerNumbers(request);
        savePage(request);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/computers/computers.jsp").forward(request,
                response);
    }

    /* Servlet */

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
