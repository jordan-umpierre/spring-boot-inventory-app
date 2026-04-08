package com.jordanportfolio.inventory.service;

import com.jordanportfolio.inventory.domain.InhousePart;
import com.jordanportfolio.inventory.domain.OutsourcedPart;
import com.jordanportfolio.inventory.repositories.InhousePartRepository;
import com.jordanportfolio.inventory.repositories.OutsourcedPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 *
 *
 */
@Service
public class InhousePartServiceImpl implements InhousePartService {
    private InhousePartRepository partRepository;

    @Autowired
    public InhousePartServiceImpl(InhousePartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public List<InhousePart> findAll() {
        return (List<InhousePart>) partRepository.findAll();
    }

    @Override
    public InhousePart findById(int theId) {
        Long theIdl=(long)theId;
        Optional<InhousePart> result = partRepository.findById(theIdl);

        InhousePart thePart = null;

        if (result.isPresent()) {
            thePart = result.get();
        }
        else {
            // we didn't find the InhousePart id
            //throw new RuntimeException("Did not find part id - " + theId);
            return null;
        }

        return thePart;
    }

    @Override
    public void save(InhousePart thePart) {
        partRepository.save(thePart);

    }

    @Override
    public void deleteById(int theId) {
        Long theIdl=(long)theId;
        partRepository.deleteById(theIdl);
    }

}

