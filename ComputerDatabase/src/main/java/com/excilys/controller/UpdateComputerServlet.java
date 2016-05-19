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
 * Servlet implementation class UpdateComputerServlet.
 */
public class UpdateComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    private Logger logger = LoggerFactory.getLogger(UpdateComputerServlet.class);

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
        List<Company> companies = companyService.findAll();
        List<DTOCompany> dtoCompanies = new ArrayList<>(companies.size());
        for (Company company : companies) {
            dtoCompanies.add(mapper.map(company));
        }
        request.setAttribute("allCompanies", companyService.findAll());
    }

    /**
     * Saves the computer to edit into the request context.
     * @param request The http request
     */
    private void saveEditableComputer(HttpServletRequest request) {
        DTOComputerMapper mapper = DTOComputerMapper.getInstance();
        // TODO Check parseLong
        long id = Long.parseLong(request.getParameter("edit"));
        DTOComputer dto = mapper.map(computerService.find(id));
        request.setAttribute("computerToEdit", dto);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("<UpdateComputerServlet> [doGet] received");
        saveAllCompanies(request);
        saveEditableComputer(request);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer/editComputer.jsp").forward(request,
                response);
    }

    /**
     * Gets computer information from POST request and update it.
     * @param request The http request
     */
    private void updateComputer(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyId = request.getParameter("companyid");
        String companyName = "";
        if (!companyId.equals("0")) {
            companyName = companyService.find(Long.parseLong(companyId)).getName();
        }
        DTOComputer dtoCpt = new DTOComputer(id, name, introduced, discontinued, companyId, companyName);
        List<String> s = DTOComputerValidator.isValideDTOComputer(dtoCpt);
        if (s.isEmpty()) {
            Computer cpt = DTOComputerMapper.getInstance().unmap(dtoCpt);
            computerService.update(cpt.getId(), cpt);
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
        logger.info("<UpdateComputerServlet> [doPost] received");
        updateComputer(request);
        response.sendRedirect("/cdb/computers");
    }

}
