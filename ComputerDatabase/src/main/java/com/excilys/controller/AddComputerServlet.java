package com.excilys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.DTOCompany;
import com.excilys.dto.DTOComputer;
import com.excilys.mapper.DTOCompanyMapper;
import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.SimpleServices;

/**
 * Servlet implementation class AddComputerServlet.
 */
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SimpleServices<Computer> computerService = new SimpleServices<>(ComputerDAO.getInstance());
    private SimpleServices<Company> companiesService = new SimpleServices<>(CompanyDAO.getInstance());

    private Logger logger = LoggerFactory.getLogger(ComputersServlet.class);

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
        if (!companyId.equals("0")) {
            companyName = companiesService.find(Long.parseLong(companyId)).getName();
        }
        DTOComputer dtoCpt = new DTOComputer(0, name, introduced, discontinued, companyId, companyName);
        Computer cpt = DTOComputerMapper.getInstance().unmap(dtoCpt);
        computerService.insert(cpt);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("ComputersServlet : [doPost]");
        insertComputer(request);
        response.sendRedirect("/cdb/computers");
    }

}
