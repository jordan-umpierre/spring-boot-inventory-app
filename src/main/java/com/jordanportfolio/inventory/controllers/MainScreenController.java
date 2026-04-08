package com.jordanportfolio.inventory.controllers;

import com.jordanportfolio.inventory.domain.Part;
import com.jordanportfolio.inventory.domain.Product;
import com.jordanportfolio.inventory.repositories.ProductRepository;
import com.jordanportfolio.inventory.service.PartService;
import com.jordanportfolio.inventory.service.ProductService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MainScreenController {
    private final ProductRepository productRepository;
    private final PartService partService;
    private final ProductService productService;

    public MainScreenController(ProductRepository productRepository, PartService partService, ProductService productService) {
        this.productRepository = productRepository;
        this.partService = partService;
        this.productService = productService;
    }

    @GetMapping("/mainscreen")
    public String listPartsandProducts(Model theModel, @Param("partkeyword") String partkeyword, @Param("productkeyword") String productkeyword) {
        List<Part> partList = partService.listAll(partkeyword);
        theModel.addAttribute("parts", partList);
        theModel.addAttribute("partkeyword", partkeyword);

        List<Product> productList = productService.listAll(productkeyword);
        theModel.addAttribute("products", productList);
        theModel.addAttribute("productkeyword", productkeyword);
        return "mainscreen";
    }

    @GetMapping("/buyproduct")
    public String buyProduct(@RequestParam("productID") long productId,
                             RedirectAttributes redirectAttributes) {
        productRepository.findById(productId).ifPresentOrElse(product -> {
            if (product.getInv() > 0) {
                product.setInv(product.getInv() - 1);
                productRepository.save(product);
                redirectAttributes.addFlashAttribute("message",
                        "Purchase successful: " + product.getName() + " inventory decreased by 1.");
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "Purchase failed: " + product.getName() + " is out of stock.");
            }
        }, () -> {
            redirectAttributes.addFlashAttribute("message", "Purchase failed: product was not found.");
        });
        return "redirect:/mainscreen";
    }
}
