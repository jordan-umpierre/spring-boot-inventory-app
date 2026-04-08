package com.jordanportfolio.inventory.bootstrap;

import com.jordanportfolio.inventory.domain.InhousePart;
import com.jordanportfolio.inventory.domain.OutsourcedPart;
import com.jordanportfolio.inventory.domain.Product;
import com.jordanportfolio.inventory.repositories.PartRepository;
import com.jordanportfolio.inventory.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BootstrapData implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BootstrapData.class);

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    public BootstrapData(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (partRepository.count() == 0 && productRepository.count() == 0) {
            log.info("Seeding starter inventory for local development.");

            InhousePart customPC = new InhousePart();
            customPC.setName("Custom Gaming PC");
            customPC.setPrice(549.99);
            customPC.setInv(8);
            customPC.setPartId(1003);
            customPC.setMin(1);
            customPC.setMax(99);

            OutsourcedPart gamingChair = new OutsourcedPart();
            gamingChair.setName("Herman Miller Embody");
            gamingChair.setPrice(399.99);
            gamingChair.setInv(6);
            gamingChair.setCompanyName("Herman Miller");
            gamingChair.setMin(1);
            gamingChair.setMax(55);

            OutsourcedPart gamingMouse = new OutsourcedPart();
            gamingMouse.setName("Logitech G Pro X Superlight");
            gamingMouse.setPrice(124.99);
            gamingMouse.setInv(12);
            gamingMouse.setCompanyName("Logitech");
            gamingMouse.setMin(1);
            gamingMouse.setMax(77);

            OutsourcedPart mousePad = new OutsourcedPart();
            mousePad.setName("Artisan Gaming Mouse Pad");
            mousePad.setPrice(89.99);
            mousePad.setInv(14);
            mousePad.setCompanyName("Artisan");
            mousePad.setMin(1);
            mousePad.setMax(88);

            InhousePart customPC2 = new InhousePart();
            customPC2.setName("Custom Gaming PC");
            customPC2.setPrice(549.99);
            customPC2.setInv(5);
            customPC2.setPartId(1004);
            customPC2.setMin(1);
            customPC2.setMax(99);

            Set<String> names = new HashSet<>();
            names.add(customPC.getName());
            names.add(gamingChair.getName());
            names.add(gamingMouse.getName());
            names.add(mousePad.getName());
            if (!names.add(customPC2.getName())) {
                customPC2.setName(customPC2.getName() + " (Multi-pack 2x)");
                partRepository.save(customPC);
                partRepository.save(gamingChair);
                partRepository.save(gamingMouse);
                partRepository.save(mousePad);
                partRepository.save(customPC2);
            }

            productRepository.save(new Product("Starter Bundle", 999.00, 5));
            productRepository.save(new Product("Competitive Bundle", 1499.00, 3));
            productRepository.save(new Product("Creator Bundle", 1299.00, 4));
            productRepository.save(new Product("Streaming Bundle", 1199.00, 4));
            productRepository.save(new Product("Ultimate Bundle", 2200.00, 8));

            log.info(
                    "Seeded {} parts and {} products for local development.",
                    partRepository.count(),
                    productRepository.count()
            );
        }
    }
}

