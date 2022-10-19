package main.controllers.datacontroller;

import main.model.data.vape.Vape;
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
    private VapeRepository vapesRepository;
    private int page;
    private String filter;
    private int size;

    @GetMapping("/low-high/vapes/")
    public List<Vape> getDataInFilteredListByLowToHighAndSize() {
        Iterable<Vape> vapesIterable = vapesRepository.findAll();

        List<Vape> unsortedVapes = new ArrayList<>();
        for(Vape vape : vapesIterable) {
            if(filter != null &&vape.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&vape.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedVapes.add(vape);
            }
            if(filter == null) {
                unsortedVapes.add(vape);
            }
        }
        size = unsortedVapes.size();
        unsortedVapes.sort(Comparator.comparing(Vape::getPriceDouble));

        List<Vape> vapes = new ArrayList<>();
        int i = 0;
        for(Vape vape : unsortedVapes) {
            i++;
            if(i >= page-99&&i <= page) {
                vapes.add(vape);
            }
        }
        return vapes;
    }

    @GetMapping("/high-low/vapes/")
    public List<Vape> getDataInFilteredListByHighToLowAndSize() {
        Iterable<Vape> vapesIterable = vapesRepository.findAll();

        List<Vape> unsortedVapes = new ArrayList<>();
        for(Vape vape : vapesIterable) {
            if(filter != null &&vape.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&vape.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedVapes.add(vape);
            }
            if(filter == null) {
                unsortedVapes.add(vape);
            }
        }
        size = unsortedVapes.size();
        unsortedVapes.sort(Comparator.comparing(Vape::getPriceDouble).reversed());

        List<Vape> vapes = new ArrayList<>();
        int i = 0;
        for(Vape vape : unsortedVapes) {
            i++;
            if(i >= page-99&&i <= page) {
                vapes.add(vape);
            }
        }
        return vapes;
    }

    @GetMapping("/default/vapes/")
    public List<Vape> getDataInFilteredListByDefaultAndSize() {
        Iterable<Vape> vapesIterable = vapesRepository.findAll();

        List<Vape> unsortedVapes = new ArrayList<>();
        for(Vape vape : vapesIterable) {
            if(filter != null &&vape.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&vape.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedVapes.add(vape);
            }
            if(filter == null) {
                unsortedVapes.add(vape);
            }
        }
        size = unsortedVapes.size();

        List<Vape> vapes = new ArrayList<>();
        int i = 0;
        for(Vape vape : unsortedVapes) {
            i++;
            if(i >= page-99&&i <= page) {
                vapes.add(vape);
            }
        }
        return vapes;
    }

    @GetMapping("/vapes/price-range/")
    public String getPriceRange(String priceFilterRange) {
        Iterable<Vape> vapesIterable = vapesRepository.findAll();

        List<Vape> unsortedVapes = new ArrayList<>();
        for(Vape vape : vapesIterable) {
            unsortedVapes.add(vape);
        }

        priceFilterRange = unsortedVapes.stream().min(Comparator.comparing(Vape::getPriceDouble)).get().getPriceDouble() + ";" +
                           unsortedVapes.stream().max(Comparator.comparing(Vape::getPriceDouble)).get().getPriceDouble();

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
