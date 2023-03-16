package main.controllers.Data;

import main.model.Data.Impl.Liquid;
import main.model.Product;
import main.repository.datarepository.LiquidRepository;
import main.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LiquidController {

    @Autowired
    private LiquidRepository liquidsRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/liquid/product/")
    public Liquid getProduct(@RequestHeader("productName") String productName) {
        return liquidsRepository.findByUrlContains(productName)
                .orElseThrow(() -> new RuntimeException("Error, Liquid not found"));
    }

    @GetMapping("/liquids/low-high/")
    public List<Product> getDataLowToHighFilter() {
        return productService.getDataLowToHighFilter(liquidsRepository.findAll());
    }

    @GetMapping("/liquids/high-low/")
    public List<Product> getDataHighToLowFilter() {
        return productService.getDataHighToLowFilter(liquidsRepository.findAll());
    }

    @GetMapping("/liquids/default/")
    public List<Product> getDataDefaultFilter() {
        return productService.getDataDefaultFilter(liquidsRepository.findAll());
    }

    @GetMapping("/liquids/price-range/")
    public String getPriceRange() {
        return productService.getPriceRange(liquidsRepository.findAll());
    }
    @GetMapping("/liquids/size/")
    public long getDataSize() {
        return productService.getDataSize();
    }
}
