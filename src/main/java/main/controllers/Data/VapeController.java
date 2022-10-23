package main.controllers.Data;

import main.model.Data.Vape;
import main.repository.datarepository.VapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
public class VapeController {

    @Autowired
    private VapeRepository vapeRepository;
    private int page;
    private String filter;
    private int size;

    @GetMapping("/low-high/vapes/")
    public List<Vape> getDataLowToHighFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();

        List<Vape> unsortedVapeArray = new ArrayList<>();
        for(Vape vape : vapesIterable) {
            if(filter != null &&vape.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&vape.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedVapeArray.add(vape);
            }
            if(filter == null) {
                unsortedVapeArray.add(vape);
            }
        }
        size = unsortedVapeArray.size();
        unsortedVapeArray.sort(Comparator.comparing(Vape::getPriceDouble));

        List<Vape> vapeArray = new ArrayList<>();
        int i = 0;
        for(Vape vape : unsortedVapeArray) {
            i++;
            if(i >= page-99&&i <= page) {
                vapeArray.add(vape);
            }
        }
        return vapeArray;
    }

    @GetMapping("/high-low/vapes/")
    public List<Vape> getDataHighToLowFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();

        List<Vape> unsortedVapeArray = new ArrayList<>();
        for(Vape vape : vapesIterable) {
            if(filter != null &&vape.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&vape.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedVapeArray.add(vape);
            }
            if(filter == null) {
                unsortedVapeArray.add(vape);
            }
        }
        size = unsortedVapeArray.size();
        unsortedVapeArray.sort(Comparator.comparing(Vape::getPriceDouble).reversed());

        List<Vape> vapeArray = new ArrayList<>();
        int i = 0;
        for(Vape vape : unsortedVapeArray) {
            i++;
            if(i >= page-99&&i <= page) {
                vapeArray.add(vape);
            }
        }
        return vapeArray;
    }

    @GetMapping("/default/vapes/")
    public List<Vape> getDataDefaultFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();

        List<Vape> unsortedVapeArray = new ArrayList<>();
        for(Vape vape : vapesIterable) {
            if(filter != null &&vape.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&vape.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedVapeArray.add(vape);
            }
            if(filter == null) {
                unsortedVapeArray.add(vape);
            }
        }
        size = unsortedVapeArray.size();

        List<Vape> vapeArray = new ArrayList<>();
        int i = 0;
        for(Vape vape : unsortedVapeArray) {
            i++;
            if(i >= page-99&&i <= page) {
                vapeArray.add(vape);
            }
        }
        return vapeArray;
    }

    @GetMapping("/vapes/price-range/")
    public String getPriceRange(String priceFilterRange) {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();

        List<Vape> unsortedVapeArray = new ArrayList<>();
        for(Vape vape : vapesIterable) {
            unsortedVapeArray.add(vape);
        }

        priceFilterRange = unsortedVapeArray.stream().min(Comparator.comparing(Vape::getPriceDouble)).get().getPriceDouble() + ";" +
                           unsortedVapeArray.stream().max(Comparator.comparing(Vape::getPriceDouble)).get().getPriceDouble();

        return priceFilterRange;
    }

    @GetMapping("/vapes/filter-low/length/")
    public long getSizeMinFilter() {
        return size;
    }

    @GetMapping("/vapes/filter-high/length/")
    public long getSizeMaxFilter() {
        return size;
    }

    @GetMapping("/vapes/filter-default/length/")
    public long getSizeDefaultFilter() {
        return size;
    }

    @PostMapping("/vapes/page/")
    public void setCurrentPage(@RequestHeader("page") int page) {
        this.page = page;
    }

    @PostMapping("/vapes/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }
}
