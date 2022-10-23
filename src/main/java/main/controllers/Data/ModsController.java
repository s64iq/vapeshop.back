package main.controllers.Data;

import main.model.Data.Mods;
import main.repository.datarepository.ModsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
public class ModsController {

    @Autowired
    private ModsRepository modsRepository;

    private int page;

    private String filter;

    private int size;

    @GetMapping("/low-high/mods/")
    public List<Mods> getDataLowToHighFilter() {
        Iterable<Mods> modsIterable = modsRepository.findAll();

        List<Mods> unsortedModArray = new ArrayList<>();
        for(Mods mod : modsIterable) {
            if(filter != null &&mod.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&mod.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedModArray.add(mod);
            }
            if(filter == null) {
                unsortedModArray.add(mod);
            }
        }
        size = unsortedModArray.size();
        unsortedModArray.sort(Comparator.comparing(Mods::getPriceDouble));

        List<Mods> modArray = new ArrayList<>();
        int i = 0;
        for(Mods mod : unsortedModArray) {
            i++;
            if(i >= page-99&&i <= page) {
                modArray.add(mod);
            }
        }
        return modArray;
    }

    @GetMapping("/high-low/mods/")
    public List<Mods> getDataHighToLowFilter() {
        Iterable<Mods> modsIterable = modsRepository.findAll();

        List<Mods> unsortedModArray = new ArrayList<>();
        for(Mods mod : modsIterable) {
            if(filter != null &&mod.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&mod.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedModArray.add(mod);
            }
            if(filter == null) {
                unsortedModArray.add(mod);
            }
        }
        size = unsortedModArray.size();
        unsortedModArray.sort(Comparator.comparing(Mods::getPriceDouble).reversed());

        List<Mods> modArray = new ArrayList<>();
        int i = 0;
        for(Mods mod : unsortedModArray) {
            i++;
            if(i >= page-99&&i <= page) {
                modArray.add(mod);
            }
        }
        return modArray;
    }

    @GetMapping("/default/mods/")
    public List<Mods> getDataDefaultFilter() {
        Iterable<Mods> modsIterable = modsRepository.findAll();

        List<Mods> unsortedModArray = new ArrayList<>();
        for(Mods mod : modsIterable) {
            if(filter != null &&mod.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&mod.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedModArray.add(mod);
            }
            if(filter == null) {
                unsortedModArray.add(mod);
            }
        }
        size = unsortedModArray.size();

        List<Mods> modArray = new ArrayList<>();
        int i = 0;
        for(Mods mod : unsortedModArray) {
            i++;
            if(i >= page-99&&i <= page) {
                modArray.add(mod);
            }
        }
        return modArray;
    }

    @GetMapping("/mods/price-range/")
    public String getPriceRange(String priceFilterRange) {
        Iterable<Mods> modsIterable = modsRepository.findAll();

        List<Mods> unsortedModArray = new ArrayList<>();
        for(Mods mod : modsIterable) {
            unsortedModArray.add(mod);
        }

        priceFilterRange = unsortedModArray.stream().min(Comparator.comparing(Mods::getPriceDouble)).get().getPriceDouble() + ";" +
                           unsortedModArray.stream().max(Comparator.comparing(Mods::getPriceDouble)).get().getPriceDouble();

        return priceFilterRange;
    }

    @GetMapping("/mods/filter-low/length/")
    public long getSizeMinFilter() {
        return size;
    }

    @GetMapping("/mods/filter-high/length/")
    public long getSizeMaxFilter() {
        return size;
    }

    @GetMapping("/mods/filter-default/length/")
    public long getSizeDefaultFilter() {
        return size;
    }

    @PostMapping("/mods/page/")
    public void setCurrentPage(@RequestHeader("page") int page) {
        this.page = page;
    }

    @PostMapping("/mods/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }
}
