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
public interface PartService {
    public List<Part> findAll();
    public Part findById(int theId);
    public void save (Part thePart);
    public void deleteById(int theId);

    public List<Part> listAll(String keyword);
}

