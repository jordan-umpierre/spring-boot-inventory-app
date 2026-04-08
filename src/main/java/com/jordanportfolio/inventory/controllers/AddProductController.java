package com.jordanportfolio.inventory.controllers;

import com.jordanportfolio.inventory.domain.Part;
import com.jordanportfolio.inventory.domain.Product;
import com.jordanportfolio.inventory.service.PartService;
import com.jordanportfolio.inventory.service.ProductService;
import com.jordanportfolio.inventory.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 *
 */
@Controller
public class AddProductController {
    @Autowired
    private ApplicationContext context;
    private final PartService partService;

    @GetMapping("/showFormAddProduct")
    public String showFormAddPart(Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        Product product = new Product();
        theModel.addAttribute("product", product);

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        theModel.addAttribute("assparts", product.getParts());
        return "productForm";
    }

    @PostMapping("/showFormAddProduct")
    public String submitForm(@Valid @ModelAttribute("product") Product product,
                             BindingResult bindingResult,
                             Model theModel) {

        // Handle validation errors
        if (bindingResult.hasErrors()) {
            theModel.addAttribute("parts", partService.findAll());
            List<Part> availParts = new ArrayList<>();
            for (Part p : partService.findAll()) {
                if (!product.getParts().contains(p)) {
                    availParts.add(p);
                }
            }
            theModel.addAttribute("availparts", availParts);
            theModel.addAttribute("assparts", product.getParts());
            return "productForm";
        }

        ProductService repo = context.getBean(ProductServiceImpl.class);

        if (product.getId() != 0) {
            Product productDB = repo.findById((int) product.getId());

            // Keep associations from DB
            if (productDB != null && productDB.getParts() != null) {
                product.setParts(productDB.getParts());
            }

            int delta = product.getInv() - productDB.getInv();

            if (delta > 0) {
                for (Part p : productDB.getParts()) {
                    int projected = p.getInv() - delta;
                    if (projected < p.getMin()) {
                        theModel.addAttribute("parts", partService.findAll());
                        List<Part> availParts = new ArrayList<>();
                        for (Part allPart : partService.findAll()) {
                            if (!productDB.getParts().contains(allPart)) {
                                availParts.add(allPart);
                            }
                        }
                        theModel.addAttribute("availparts", availParts);
                        theModel.addAttribute("assparts", productDB.getParts());
                        theModel.addAttribute("formError",
                                "Cannot save: increasing product inventory by " + delta +
                                        " would drop part '" + p.getName() + "' below its minimum (" + p.getMin() + ").");
                        return "productForm";
                    }
                }

                // Apply inventory decrease to each associated part
                for (Part p : productDB.getParts()) {
                    p.setInv(p.getInv() - delta);
                    partService.save(p); // Persist change to part
                }
            }
        }

        repo.save(product);
        return "confirmationaddproduct";
    }

    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") int theId, Model theModel) {
        theModel.addAttribute("parts", partService.findAll());

        ProductService repo = context.getBean(ProductServiceImpl.class);
        Product theProduct = repo.findById(theId);

        theModel.addAttribute("product", theProduct);
        theModel.addAttribute("assparts", theProduct.getParts());

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!theProduct.getParts().contains(p)) {
                availParts.add(p);
            }
        }
        theModel.addAttribute("availparts", availParts);

        return "productForm";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") int theId, Model theModel) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product2 = productService.findById(theId);
        for (Part part : product2.getParts()) {
            part.getProducts().remove(product2);
            partService.save(part);
        }
        product2.getParts().removeAll(product2.getParts());
        productService.save(product2);
        productService.deleteById(theId);

        return "confirmationdeleteproduct";
    }

    public AddProductController(PartService partService) {
        this.partService = partService;
    }

    // Associate a part with the product shown on the form
    @GetMapping("/associatepart")
    public String associatePart(@RequestParam("partID") int theID,
                                @RequestParam("productID") int productID,
                                Model theModel) {

        // Always load DB-managed entities
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product = productService.findById(productID);
        Part part = partService.findById(theID);

        if (product == null || part == null) {
            theModel.addAttribute("formError", "Could not find product or part to associate.");
            return "productForm";
        }

        // Update both sides, save the owning side (Part)
        product.getParts().add(part);
        part.getProducts().add(product);
        partService.save(part);            // owning side persists

        // Rebuild model
        theModel.addAttribute("product", product);
        theModel.addAttribute("assparts", product.getParts());

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product.getParts().contains(p)) {
                availParts.add(p);
            }
        }
        theModel.addAttribute("availparts", availParts);

        return "productForm";
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int theID,
                             @RequestParam("productID") int productID,
                             Model theModel) {

        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product = productService.findById(productID);
        Part part = partService.findById(theID);

        if (product == null || part == null) {
            theModel.addAttribute("formError", "Could not find product or part to remove.");
            return "productForm";
        }

        // Update both sides, save the owning side (Part)
        product.getParts().remove(part);
        part.getProducts().remove(product);
        partService.save(part);            // <-- owning side persist

        // Rebuild model
        theModel.addAttribute("product", product);
        theModel.addAttribute("assparts", product.getParts());

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product.getParts().contains(p)) {
                availParts.add(p);
            }
        }
        theModel.addAttribute("availparts", availParts);

        return "productForm";
    }
}

