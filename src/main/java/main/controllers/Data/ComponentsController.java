package main.controllers.Data;

import main.model.Data.Components;
import main.repository.datarepository.ComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

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

        componentsIterable.iterator().forEachRemaining(unsortedComponentArray::add);

        if(filter != null) {
            unsortedComponentArray = unsortedComponentArray.stream().filter(component ->
                    component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedComponentArray.size();
        unsortedComponentArray.sort(Comparator.comparing(Components::getPriceDouble));

        List<Components> componentArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                componentArray.add(unsortedComponentArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    componentArray.add(unsortedComponentArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    componentArray.add(unsortedComponentArray.get(i));
                }
            }
        }

        return componentArray;
    }

    @GetMapping("/high-low/components/")
    public List<Components> getDataHighToLowFilter() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();
        List<Components> unsortedComponentArray = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(unsortedComponentArray::add);

        if(filter != null) {
            unsortedComponentArray = unsortedComponentArray.stream().filter(component ->
                    component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedComponentArray.size();
        unsortedComponentArray.sort(Comparator.comparing(Components::getPriceDouble).reversed());

        List<Components> componentArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                componentArray.add(unsortedComponentArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    componentArray.add(unsortedComponentArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    componentArray.add(unsortedComponentArray.get(i));
                }
            }
        }

        return componentArray;
    }

    @GetMapping("/default/components/")
    public List<Components> getDataDefaultFilter() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();
        List<Components> unsortedComponentArray = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(unsortedComponentArray::add);

        if(filter != null) {
            unsortedComponentArray = unsortedComponentArray.stream().filter(component ->
                    component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedComponentArray.size();

        List<Components> componentArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                componentArray.add(unsortedComponentArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    componentArray.add(unsortedComponentArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    componentArray.add(unsortedComponentArray.get(i));
                }
            }
        }

        return componentArray;
    }

    @GetMapping("/components/price-range/")
    public String getPriceRange() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();
        List<Components> unsortedComponentArray = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(unsortedComponentArray::add);

        return unsortedComponentArray.stream().min(Comparator.comparing(Components::getPriceDouble)).get().getPriceDouble() + ";" +
                unsortedComponentArray.stream().max(Comparator.comparing(Components::getPriceDouble)).get().getPriceDouble();
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
        this.page = page * 100;
    }

    @PostMapping("/components/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }
}
