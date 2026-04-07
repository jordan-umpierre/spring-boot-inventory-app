package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.service.*;
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
public class AddOutsourcedPartController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/showFormAddOutPart")
    public String showFormAddOutsourcedPart(Model theModel){
        Part part=new OutsourcedPart();
        theModel.addAttribute("outsourcedpart",part);
        return "OutsourcedPartForm";
    }

    @PostMapping("/showFormAddOutPart")
    public String submitForm(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult theBindingResult, Model theModel) {
        theModel.addAttribute("outsourcedpart", part);
        if (theBindingResult.hasErrors()) {
            return "OutsourcedPartForm";
        }

        if (part.getMin() > part.getMax() || part.getInv() < part.getMin() || part.getInv() > part.getMax()) {
            theModel.addAttribute("formError",
                    "Inventory must be between Min and Max, and Min must be ≤ Max.");
            return "OutsourcedPartForm";
        }

        OutsourcedPartService repo=context.getBean(OutsourcedPartServiceImpl.class);
        //InhousePart ip=repo.findById((int)part.getId());
        //if (ip != null) {
        //    part.setProducts(ip.getProducts());
        //}
        repo.save(part);
        return "confirmationaddpart";
    }
}