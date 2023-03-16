package main.controllers.Data;

import main.model.Data.Impl.Component;
import main.model.Product;
import main.repository.datarepository.ComponentRepository;
import main.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ComponentController {
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/component/product/")
    public Component getProduct(@RequestHeader("productName") String productName) {
        return componentRepository.findByUrlContains(productName)
                .orElseThrow(() -> new RuntimeException("Error, Component not found"));
    }

    @GetMapping("/components/low-high/")
    public List<Product> getDataLowToHighFilter() {
        return productService.getDataLowToHighFilter(componentRepository.findAll());
    }

    @GetMapping("/components/high-low/")
    public List<Product> getDataHighToLowFilter() {
        return productService.getDataHighToLowFilter(componentRepository.findAll());
    }

    @GetMapping("/components/default/")
    public List<Product> getDataDefaultFilter() {
        return productService.getDataDefaultFilter(componentRepository.findAll());
    }

    @GetMapping("/components/price-range/")
    public String getPriceRange() {
        return productService.getPriceRange(componentRepository.findAll());
    }

    @GetMapping("/components/size/")
    public long getDataSize() {
        return productService.getDataSize();
    }
}
