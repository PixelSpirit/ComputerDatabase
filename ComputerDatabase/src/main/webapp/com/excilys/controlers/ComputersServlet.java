package com.excilys.controlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.ServiceException;
import com.excilys.service.SimpleServices;

/**
 * Servlet implementation class ComputersServlet
 */
public class ComputersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SimpleServices<Computer> computerService = new SimpleServices<>(ComputerDAO.getInstance());
    // private SimpleServices<Company> companyService = new
    // SimpleServices<>(CompanyDAO.getInstance());

    private Logger logger = LoggerFactory.getLogger(ComputersServlet.class);

    private void saveComputerNumbers(HttpServletRequest request) throws ServiceException {
        request.setAttribute("computerNumber", computerService.count());
    }

    private void savePage(HttpServletRequest request) throws ServiceException {
        try {
            int number = Integer.parseInt(request.getParameter("page"));
            int size = Integer.parseInt(request.getParameter("limit"));
            // TODO : Use DTO
            request.setAttribute("page", computerService.findPage(number, size));
        } catch (NullPointerException | NumberFormatException e) {
            request.setAttribute("page", computerService.findPage(0, 10));
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
