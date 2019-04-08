package com.epam.courses.paycom.web_app;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    private CompanyService companyService;

    @Autowired
    public CompanyController (CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping(value = "/companies")
    public final String companies(Model model) {

        LOGGER.debug("findAll({})", model);
        model.addAttribute("companies", companyService.findAll());
        return "companies";
    }

    @GetMapping(value = "/company")
    public String company(Model model) {
        Company company = new Company();
        model.addAttribute("isNew", true);
        model.addAttribute("company", company);
        return "company";
    }

    @PostMapping(value = "/company")
    public String addCompany( Company company) {
        LOGGER.debug("addCompany({})", company);
            companyService.add(company);
            return "redirect:/companies";
    }

    @GetMapping(value = "/company/{id}")
    public String gotoEditCompanyPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoEditCompanyPage({}, {})", id, model);
        model.addAttribute("isNew", false);
        Company company = companyService.findById(id);
        model.addAttribute("company", company);

        return "company";
    }

    @PostMapping(value = "/company/{id}")
    public String updateCompany(Company company) {
        LOGGER.debug("updateCompany({})", company);
        companyService.update(company);
        return "redirect:/companies";
    }

    @GetMapping(value = "/company/{id}/delete")
    public final String deleteCompanyById(@PathVariable Integer id, Model model) {
        LOGGER.debug("delete({},{})", id, model);
        companyService.delete(id);
        return "redirect:/companies";
    }
}
