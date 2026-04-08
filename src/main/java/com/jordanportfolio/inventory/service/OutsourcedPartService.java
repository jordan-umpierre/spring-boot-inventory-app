package com.jordanportfolio.inventory.service;

import com.jordanportfolio.inventory.domain.OutsourcedPart;
import com.jordanportfolio.inventory.domain.Part;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface OutsourcedPartService {
        public List<OutsourcedPart> findAll();
        public OutsourcedPart findById(int theId);
        public void save (OutsourcedPart thePart);
        public void deleteById(int theId);
}

