package com.excilys.controlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.service.ServiceException;
import com.excilys.service.SimpleServices;

/**
 * Servlet implementation class AddComputerServlet
 */
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SimpleServices<Company> companiesService = new SimpleServices<>(CompanyDAO.getInstance());

    private Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

    private void saveAllCompanies(HttpServletRequest request) throws ServiceException {
        request.setAttribute("allCompanies", companiesService.findAll());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            saveAllCompanies(request);
            this.getServletContext().getRequestDispatcher("/views/addComputer/addComputer.jsp").forward(request,
                    response);
        } catch (ServiceException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            this.getServletContext().getRequestDispatcher("/views/404.html").forward(request, response);
        }
    }

}
