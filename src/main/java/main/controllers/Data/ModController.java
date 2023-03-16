package main.controllers.Data;

import main.model.Data.Impl.Mod;
import main.model.Product;
import main.repository.datarepository.ModRepository;
import main.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ModController {

    @Autowired
    private ModRepository modRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/mod/product/")
    public Mod getProduct(@RequestHeader("productName") String productName) {
        return modRepository.findByUrlContains(productName)
                .orElseThrow(() -> new RuntimeException("Error, Mod not found"));
    }

    @GetMapping("/mods/low-high/")
    public List<Product> getDataLowToHighFilter() {
        return productService.getDataLowToHighFilter(modRepository.findAll());
    }

    @GetMapping("/mods/high-low/")
    public List<Product> getDataHighToLowFilter() {
        return productService.getDataHighToLowFilter(modRepository.findAll());
    }

    @GetMapping("/mods/default/")
    public List<Product> getDataDefaultFilter() {
        return productService.getDataDefaultFilter(modRepository.findAll());
    }

    @GetMapping("/mods/price-range/")
    public String getPriceRange() {
        return productService.getPriceRange(modRepository.findAll());
    }

    @GetMapping("/mods/size/")
    public long getDataSize() {
        return productService.getDataSize();
    }
}
