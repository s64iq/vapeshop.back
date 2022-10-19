package main.controllers.datacontroller;

import main.model.data.components.Components;
import main.model.data.mods.Mods;
import main.model.data.vape.Vape;
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
    public List<Mods> getDataInFilteredListByLowToHighAndSize() {
        Iterable<Mods> modsIterable = modsRepository.findAll();

        List<Mods> unsortedMods = new ArrayList<>();
        for(Mods mod : modsIterable) {
            if(filter != null &&mod.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&mod.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedMods.add(mod);
            }
            if(filter == null) {
                unsortedMods.add(mod);
            }
        }
        size = unsortedMods.size();
        unsortedMods.sort(Comparator.comparing(Mods::getPriceDouble));

        List<Mods> mods = new ArrayList<>();
        int i = 0;
        for(Mods mod : unsortedMods) {
            i++;
            if(i >= page-99&&i <= page) {
                mods.add(mod);
            }
        }
        return mods;
    }

    @GetMapping("/high-low/mods/")
    public List<Mods> getDataInFilteredListByHighToLowAndSize() {
        Iterable<Mods> modsIterable = modsRepository.findAll();

        List<Mods> unsortedMods = new ArrayList<>();
        for(Mods mod : modsIterable) {
            if(filter != null &&mod.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&mod.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedMods.add(mod);
            }
            if(filter == null) {
                unsortedMods.add(mod);
            }
        }
        size = unsortedMods.size();
        unsortedMods.sort(Comparator.comparing(Mods::getPriceDouble).reversed());

        List<Mods> mods = new ArrayList<>();
        int i = 0;
        for(Mods mod : unsortedMods) {
            i++;
            if(i >= page-99&&i <= page) {
                mods.add(mod);
            }
        }
        return mods;
    }

    @GetMapping("/default/mods/")
    public List<Mods> getDataInFilteredListByDefaultAndSize() {
        Iterable<Mods> modsIterable = modsRepository.findAll();

        List<Mods> unsortedMods = new ArrayList<>();
        for(Mods mod : modsIterable) {
            if(filter != null &&mod.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&mod.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedMods.add(mod);
            }
            if(filter == null) {
                unsortedMods.add(mod);
            }
        }
        size = unsortedMods.size();

        List<Mods> mods = new ArrayList<>();
        int i = 0;
        for(Mods mod : unsortedMods) {
            i++;
            if(i >= page-99&&i <= page) {
                mods.add(mod);
            }
        }
        return mods;
    }

    @GetMapping("/mods/price-range/")
    public String getPriceRange(String priceFilterRange) {
        Iterable<Mods> modsIterable = modsRepository.findAll();

        List<Mods> unsortedMods = new ArrayList<>();
        for(Mods mod : modsIterable) {
            unsortedMods.add(mod);
        }

        priceFilterRange = unsortedMods.stream().min(Comparator.comparing(Mods::getPriceDouble)).get().getPriceDouble() + ";" +
                           unsortedMods.stream().max(Comparator.comparing(Mods::getPriceDouble)).get().getPriceDouble();

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
