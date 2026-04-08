package com.jordanportfolio.inventory.service;

import com.jordanportfolio.inventory.domain.Part;
import com.jordanportfolio.inventory.domain.Product;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface ProductService {
    public List<Product> findAll();
    public Product findById(int theId);
    public void save (Product theProduct);
    public void deleteById(int theId);
    public List<Product> listAll(String keyword);

}

