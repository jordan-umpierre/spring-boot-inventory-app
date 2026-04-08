package com.jordanportfolio.inventory.controllers;

import com.jordanportfolio.inventory.domain.InhousePart;
import com.jordanportfolio.inventory.domain.Part;
import com.jordanportfolio.inventory.service.InhousePartService;
import com.jordanportfolio.inventory.service.InhousePartServiceImpl;
import com.jordanportfolio.inventory.service.PartService;
import com.jordanportfolio.inventory.service.PartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 *
 *
 *
 *
 */
@Controller
public class AddInhousePartController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/showFormAddInPart")
    public String showFormAddInhousePart(Model theModel) {
        InhousePart inhousepart = new InhousePart();
        theModel.addAttribute("inhousepart", inhousepart);
        return "InhousePartForm";
    }

    @PostMapping("/showFormAddInPart")
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult theBindingResult, Model theModel) {
        theModel.addAttribute("inhousepart", part);
        if (theBindingResult.hasErrors()) {
            return "InhousePartForm";
        }

        if (part.getMin() > part.getMax() || part.getInv() < part.getMin() || part.getInv() > part.getMax()) {
            theModel.addAttribute("formError",
                    "Inventory must be between Min and Max, and Min must be â‰¤ Max.");
            return "InhousePartForm";
        }

        InhousePartService repo=context.getBean(InhousePartServiceImpl.class);
        //InhousePart ip=repo.findById((int)part.getId());
        //if (ip != null) {
        //    part.setProducts(ip.getProducts());
        //}
        repo.save(part);
        return "confirmationaddpart";
    }
}
