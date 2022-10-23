package main.controllers.Data;

import main.model.Data.Components;
import main.repository.datarepository.ComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@CrossOrigin
public class ComponentsController {
    @Autowired
    private ComponentsRepository componentsRepository;

    private int page;

    private String filter;

    private int size;

    @GetMapping("/low-high/components/")
    public List<Components> getDataLowToHighFilter() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();

        List<Components> unsortedComponentArray = new ArrayList<>();
        for(Components component : componentsIterable) {
            if(filter != null &&component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedComponentArray.add(component);
            }
            if(filter == null) {
                unsortedComponentArray.add(component);
            }
        }

        size = unsortedComponentArray.size();
        unsortedComponentArray.sort(Comparator.comparing(Components::getPriceDouble));

        List<Components> componentArray = new ArrayList<>();
        int i = 0;
        for(Components component : unsortedComponentArray) {
            i++;
            if(i >= page-99&&i <= page) {
                componentArray.add(component);
            }
        }
        return componentArray;
    }

    @GetMapping("/high-low/components/")
    public List<Components> getDataHighToLowFilter() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();

        List<Components> unsortedComponentArray = new ArrayList<>();
        for(Components component : componentsIterable) {
            if(filter != null &&component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedComponentArray.add(component);
            }
            if(filter == null) {
                unsortedComponentArray.add(component);
            }
        }
        size = unsortedComponentArray.size();
        unsortedComponentArray.sort(Comparator.comparing(Components::getPriceDouble).reversed());

        List<Components> componentArray = new ArrayList<>();
        int i = 0;
        for(Components component : unsortedComponentArray) {
            i++;
            if(i >= page-99&&i <= page) {
                componentArray.add(component);
            }
        }
        return componentArray;
    }

    @GetMapping("/default/components/")
    public List<Components> getDataDefaultFilter() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();

        List<Components> unsortedComponentArray = new ArrayList<>();
        for(Components component : componentsIterable) {
            if(filter != null &&component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedComponentArray.add(component);
            }
            if(filter == null) {
                unsortedComponentArray.add(component);
            }
        }
        size = unsortedComponentArray.size();

        List<Components> componentArray = new ArrayList<>();
        int i = 0;
        for(Components component : unsortedComponentArray) {
            i++;
            if(i >= page-99&&i <= page) {
                componentArray.add(component);
            }
        }
        return componentArray;
    }

    @GetMapping("/components/price-range/")
    public String getPriceRange(String priceFilterRange) {
        Iterable<Components> componentsIterable = componentsRepository.findAll();

        List<Components> unsortedComponentArray = new ArrayList<>();
        for(Components component : componentsIterable) {
            unsortedComponentArray.add(component);
        }

        priceFilterRange = unsortedComponentArray.stream().min(Comparator.comparing(Components::getPriceDouble)).get().getPriceDouble() + ";" +
                           unsortedComponentArray.stream().max(Comparator.comparing(Components::getPriceDouble)).get().getPriceDouble();

        return priceFilterRange;
    }

    @GetMapping("/components/filter-low/length/")
    public long getSizeMinFilter() {
        return size;
    }

    @GetMapping("/components/filter-high/length/")
    public long getSizeMaxFilter() {
        return size;
    }

    @GetMapping("/components/filter-default/length/")
    public long getSizeDefaultFilter() {
        return size;
    }

    @PostMapping("/components/page/")
    public void setCurrentPage(@RequestHeader("page") int page) {
        this.page = page;
    }

    @PostMapping("/components/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }
}
