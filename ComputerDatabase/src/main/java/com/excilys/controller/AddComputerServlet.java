package com.excilys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.dto.DTOCompany;
import com.excilys.dto.DTOComputer;
import com.excilys.mapper.DTOCompanyMapper;
import com.excilys.mapper.DTOComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.DTOComputerValidator;

/**
 * Servlet implementation class AddComputerServlet.
 */
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companiesService;

    private Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

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
        logger.info("<AddComputerServlet> [doGet] received");
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
        if (companyId != null && !companyId.equals("0")) {
            companyName = companiesService.find(Long.parseLong(companyId)).getName();
        }
        DTOComputer dto = new DTOComputer("", name, introduced, discontinued, companyId, companyName);
        List<String> s = DTOComputerValidator.isValideDTOComputer(dto);
        if (s.isEmpty()) {
            Computer cpt = DTOComputerMapper.getInstance().unmap(dto);
            computerService.insert(cpt);
        } else {
            // TODO : Print error in JSP
            for (String string : s) {
                System.out.println(string);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("<AddComputerServlet> [doPost] received");
        insertComputer(request);
        response.sendRedirect("/cdb/computers");
    }

}
