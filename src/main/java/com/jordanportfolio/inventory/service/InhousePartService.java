package com.jordanportfolio.inventory.service;

import com.jordanportfolio.inventory.domain.InhousePart;
import com.jordanportfolio.inventory.domain.OutsourcedPart;
import com.jordanportfolio.inventory.domain.Part;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface InhousePartService {
    public List<InhousePart> findAll();
    public InhousePart findById(int theId);
    public void save (InhousePart thePart);
    public void deleteById(int theId);
}

