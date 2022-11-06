package main.controllers.Data;

import main.model.Data.Components;
import main.model.Data.Mods;
import main.repository.datarepository.ModsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        modsIterable.iterator().forEachRemaining(unsortedModArray::add);

        if(filter != null) {
            unsortedModArray = unsortedModArray.stream().filter(component ->
                    component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedModArray.size();
        unsortedModArray.sort(Comparator.comparing(Mods::getPriceDouble));

        List<Mods> modArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                modArray.add(unsortedModArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    modArray.add(unsortedModArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    modArray.add(unsortedModArray.get(i));
                }
            }
        }

        return modArray;
    }

    @GetMapping("/high-low/mods/")
    public List<Mods> getDataHighToLowFilter() {
        Iterable<Mods> modsIterable = modsRepository.findAll();
        List<Mods> unsortedModArray = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(unsortedModArray::add);

        if(filter != null) {
            unsortedModArray = unsortedModArray.stream().filter(component ->
                    component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedModArray.size();
        unsortedModArray.sort(Comparator.comparing(Mods::getPriceDouble).reversed());

        List<Mods> modArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                modArray.add(unsortedModArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    modArray.add(unsortedModArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    modArray.add(unsortedModArray.get(i));
                }
            }
        }

        return modArray;
    }

    @GetMapping("/default/mods/")
    public List<Mods> getDataDefaultFilter() {
        Iterable<Mods> modsIterable = modsRepository.findAll();
        List<Mods> unsortedModArray = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(unsortedModArray::add);

        if(filter != null) {
            unsortedModArray = unsortedModArray.stream().filter(component ->
                    component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedModArray.size();

        List<Mods> modArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                modArray.add(unsortedModArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    modArray.add(unsortedModArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    modArray.add(unsortedModArray.get(i));
                }
            }
        }

        return modArray;
    }

    @GetMapping("/mods/price-range/")
    public String getPriceRange() {
        Iterable<Mods> modsIterable = modsRepository.findAll();
        List<Mods> unsortedModArray = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(unsortedModArray::add);

        return unsortedModArray.stream().min(Comparator.comparing(Mods::getPriceDouble)).get().getPriceDouble() + ";" +
                unsortedModArray.stream().max(Comparator.comparing(Mods::getPriceDouble)).get().getPriceDouble();
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
        this.page = page * 100;
    }

    @PostMapping("/mods/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }
}
