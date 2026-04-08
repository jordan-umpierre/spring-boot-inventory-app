package com.jordanportfolio.inventory.service;

import com.jordanportfolio.inventory.domain.InhousePart;
import com.jordanportfolio.inventory.repositories.InhousePartRepository;
import com.jordanportfolio.inventory.repositories.PartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InhousePartServiceTest {
    InhousePartRepository inhousePartRepository;
    InhousePartService inhousePartService;
    @BeforeEach
    void setUp() {
        inhousePartRepository=mock(InhousePartRepository.class);
        inhousePartService=new InhousePartServiceImpl(inhousePartRepository);
    }

    @Test
    void findAll() {
        InhousePart part=new InhousePart();
        List partData=new ArrayList();
        partData.add(part);
        when(inhousePartRepository.findAll()).thenReturn(partData);
        List<InhousePart> parts=inhousePartService.findAll();
        assertEquals(partData.size(),1);
    }
}

