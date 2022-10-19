package main.controllers.datacontroller;

import main.model.data.components.Components;
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
    public List<Components> getDataInFilteredListByLowToHighAndSize() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();

        List<Components> unsortedComponents = new ArrayList<>();
        for(Components component : componentsIterable) {
            if(filter != null &&component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedComponents.add(component);
            }
            if(filter == null) {
                unsortedComponents.add(component);
            }
        }

        size = unsortedComponents.size();
        unsortedComponents.sort(Comparator.comparing(Components::getPriceDouble));

        List<Components> components = new ArrayList<>();
        int i = 0;
        for(Components component : unsortedComponents) {
            i++;
            if(i >= page-99&&i <= page) {
                components.add(component);
            }
        }
        return components;
    }

    @GetMapping("/high-low/components/")
    public List<Components> getDataInFilteredListByHighToLowAndSize() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();

        List<Components> unsortedComponents = new ArrayList<>();
        for(Components component : componentsIterable) {
            if(filter != null &&component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedComponents.add(component);
            }
            if(filter == null) {
                unsortedComponents.add(component);
            }
        }
        size = unsortedComponents.size();
        unsortedComponents.sort(Comparator.comparing(Components::getPriceDouble).reversed());

        List<Components> components = new ArrayList<>();
        int i = 0;
        for(Components component : unsortedComponents) {
            i++;
            if(i >= page-99&&i <= page) {
                components.add(component);
            }
        }
        return components;
    }

    @GetMapping("/default/components/")
    public List<Components> getDataInFilteredListByDefaultAndSize() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();

        List<Components> unsortedComponents = new ArrayList<>();
        for(Components component : componentsIterable) {
            if(filter != null &&component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedComponents.add(component);
            }
            if(filter == null) {
                unsortedComponents.add(component);
            }
        }
        size = unsortedComponents.size();

        List<Components> components = new ArrayList<>();
        int i = 0;
        for(Components component : unsortedComponents) {
            i++;
            if(i >= page-99&&i <= page) {
                components.add(component);
            }
        }
        return components;
    }

    @GetMapping("/components/price-range/")
    public String getPriceRange(String priceFilterRange) {
        Iterable<Components> componentsIterable = componentsRepository.findAll();

        List<Components> unsortedComponents = new ArrayList<>();
        for(Components component : componentsIterable) {
            unsortedComponents.add(component);
        }

        priceFilterRange = unsortedComponents.stream().min(Comparator.comparing(Components::getPriceDouble)).get().getPriceDouble() + ";" +
                           unsortedComponents.stream().max(Comparator.comparing(Components::getPriceDouble)).get().getPriceDouble();

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
