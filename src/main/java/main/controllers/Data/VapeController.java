package main.controllers.Data;

import main.model.Data.Impl.Vape;
import main.model.Product;
import main.repository.datarepository.VapeRepository;
import main.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class VapeController {

    @Autowired
    private VapeRepository vapeRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/vape/product/")
    public Vape getProduct(@RequestHeader("productName") String productName) {
        return vapeRepository.findByUrlContains(productName)
                .orElseThrow(() -> new RuntimeException("Error, Mod not found"));
    }

    @GetMapping("/vapes/low-high/")
    public List<Product> getDataLowToHighFilter() {
        return productService.getDataLowToHighFilter(vapeRepository.findAll());
    }

    @GetMapping("/vapes/high-low/")
    public List<Product> getDataHighToLowFilter() {
        return productService.getDataHighToLowFilter(vapeRepository.findAll());
    }

    @GetMapping("/vapes/default/")
    public List<Product> getDataDefaultFilter() {
        return productService.getDataDefaultFilter(vapeRepository.findAll());
    }

    @GetMapping("/vapes/price-range/")
    public String getPriceRange() {
        return productService.getPriceRange(vapeRepository.findAll());
    }

    @GetMapping("/vapes/size/")
    public long getDataSize() {
        return productService.getDataSize();
    }
}
