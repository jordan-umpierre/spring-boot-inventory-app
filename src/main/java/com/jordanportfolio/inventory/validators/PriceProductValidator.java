package com.jordanportfolio.inventory.validators;

import com.jordanportfolio.inventory.domain.Part;
import com.jordanportfolio.inventory.domain.Product;
import com.jordanportfolio.inventory.repositories.InhousePartRepository;
import com.jordanportfolio.inventory.repositories.ProductRepository;
import com.jordanportfolio.inventory.service.InhousePartServiceImpl;
import com.jordanportfolio.inventory.service.ProductService;
import com.jordanportfolio.inventory.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 *
 *
 *
 */
public class PriceProductValidator implements ConstraintValidator<ValidProductPrice, Product> {
    @Autowired
    private ApplicationContext context;

    public static  ApplicationContext myContext;

    @Override
    public void initialize(ValidProductPrice constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext constraintValidatorContext) {
        if(context==null) return true;
        if(context!=null)myContext=context;
        ProductService repo = myContext.getBean(ProductServiceImpl.class);
        double sumPartsPrice = 0;
        if (product.getId() != 0) {
            Product myProduct = repo.findById((int) product.getId());
            for (Part p : myProduct.getParts()) sumPartsPrice = sumPartsPrice + p.getPrice();
            if (product.getPrice() >= sumPartsPrice) {
                return true;
            } else {
                return false;
            }
        }
        else {
            return true;
        }
    }
}

