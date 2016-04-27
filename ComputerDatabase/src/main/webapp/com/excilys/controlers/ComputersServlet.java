package com.excilys.controlers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.DTOComputer;
import com.excilys.model.Page;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.ServiceException;
import com.excilys.service.SimpleServices;

/**
 * Servlet implementation class ComputersServlet
 */
public class ComputersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SimpleServices<Computer> computerService = new SimpleServices<>(ComputerDAO.getInstance());

    private Logger logger = LoggerFactory.getLogger(ComputersServlet.class);

    private void saveComputerNumbers(HttpServletRequest request) throws ServiceException {
        request.setAttribute("computerNumber", computerService.count());
    }

    private Page<DTOComputer> fromComputers(Page<Computer> page) {
        DTOComputerMapper mapper = DTOComputerMapper.getInstance();
        Page<DTOComputer> dtoPage = new Page<DTOComputer>(page.getNumber(), page.getSize(),
                new ArrayList<>(page.getContent().size()));
        for (Computer computer : page.getContent()) {
            dtoPage.getContent().add(mapper.map(computer));
        }
        return dtoPage;
    }

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

    /* Servlet */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            saveComputerNumbers(request);
            savePage(request);
            this.getServletContext().getRequestDispatcher("/views/computers.jsp").forward(request, response);
        } catch (ServiceException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            this.getServletContext().getRequestDispatcher("/views/404.html").forward(request, response);
        }
    }

}
