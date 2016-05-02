package com.excilys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dto.DTOCompany;
import com.excilys.mapper.DTOCompanyMapper;
import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.service.SimpleServices;

/**
 * Servlet implementation class AddComputerServlet.
 */
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SimpleServices<Company> companiesService = new SimpleServices<>(CompanyDAO.getInstance());

    /**
     * Saves all DTOCompanies reachable from services into the request context.
     * @param request The http request
     * @throws ServiceException if if service is unavailable
     */
    private void saveAllCompanies(HttpServletRequest request) {
        DTOCompanyMapper mapper = DTOCompanyMapper.getInstance();
        List<Company> companies = companiesService.findAll();
        List<DTOCompany> dtoCompanies = new ArrayList<>(companies.size());
        for (Company company : companies) {
            dtoCompanies.add(mapper.map(company));
        }
        request.setAttribute("allCompanies", companiesService.findAll());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        saveAllCompanies(request);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer/addComputer.jsp").forward(request,
                response);
    }

}
