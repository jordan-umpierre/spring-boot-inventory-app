package com.jordanportfolio.inventory.bootstrap;

import com.jordanportfolio.inventory.domain.OutsourcedPart;
import com.jordanportfolio.inventory.domain.Product;
import com.jordanportfolio.inventory.repositories.OutsourcedPartRepository;
import com.jordanportfolio.inventory.repositories.PartRepository;
import com.jordanportfolio.inventory.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.jordanportfolio.inventory.domain.InhousePart;

import java.util.List;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository = outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Sample Inventory only when part and product lists are empty
        if (partRepository.count() == 0 && productRepository.count() == 0) {
            System.out.println("Seeding initial Gamer Parts & Gamer Packages...");
            // Declaring 5 parts
            InhousePart customPC = new InhousePart();
            customPC.setName("Custom Built ORIGIN PC");
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
            gamingMouse.setName("Logitech SUPERLIGHT");
            gamingMouse.setPrice(124.99);
            gamingMouse.setInv(12);
            gamingMouse.setCompanyName("Logitech");
            gamingMouse.setMin(1);
            gamingMouse.setMax(77);

            OutsourcedPart mousePad = new OutsourcedPart();
            mousePad.setName("FaZe Jev Pad");
            mousePad.setPrice(89.99);
            mousePad.setInv(14);
            mousePad.setCompanyName("FaZe");
            mousePad.setMin(1);
            mousePad.setMax(88);

            // Duplicate name to trigger "Multi-pack"
            InhousePart customPC2 = new InhousePart();
            customPC2.setName("Custom Built ORIGIN PC");
            customPC2.setPrice(549.99);
            customPC2.setInv(5);
            customPC2.setPartId(1004);
            customPC2.setMin(1);
            customPC2.setMax(99);

            // Convert duplicate to "Multi-pack 2x"
            java.util.Set<String> names = new java.util.HashSet<>();
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
                System.out.println("Seeded 5 parts.");
            } else {
                System.out.println("Skipping seed: existing data detected.");
            }

            List<OutsourcedPart> outsourcedParts = (List<OutsourcedPart>) outsourcedPartRepository.findAll();
            for (OutsourcedPart part : outsourcedParts) {
                System.out.println(part.getName() + " " + part.getCompanyName());
            }

            // Declaring 5 Products
            Product noobPackage = new Product("Noob Package", 999.00, 5);
            productRepository.save(noobPackage);
            Product recruitPackage = new Product("Recruit Package", 1499.00, 3);
            productRepository.save(recruitPackage);
            Product normalPackage = new Product("Normal Package", 1299.00, 4);
            productRepository.save(normalPackage);
            Product elitePackage = new Product("Elite Package", 1199.00, 4);
            productRepository.save(elitePackage);
            Product veteranPackage = new Product("Veteran Package", 2200.00, 8);
            productRepository.save(veteranPackage);

            System.out.println("Seeded 5 products.");

            System.out.println("Started in Bootstrap");
            System.out.println("Number of Products" + productRepository.count());
            System.out.println(productRepository.findAll());
            System.out.println("Number of Parts" + partRepository.count());
            System.out.println(partRepository.findAll());
        }
    }
}

