package main.controllers.Data;

import main.model.Data.Impl.Components;
import main.repository.datarepository.ComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        List<Components> componentsList = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(componentsList::add);

        componentsList = filterData(componentsList);
        componentsList.sort(Comparator.comparing(Components::getPrice));

        size = componentsList.size();

        return getSortedData(componentsList,size);
    }

    @GetMapping("/high-low/components/")
    public List<Components> getDataHighToLowFilter() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();
        List<Components> componentsList = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(componentsList::add);

        componentsList = filterData(componentsList);
        componentsList.sort(Comparator.comparing(Components::getPrice).reversed());

        size = componentsList.size();

        return getSortedData(componentsList,size);
    }

    @GetMapping("/default/components/")
    public List<Components> getDataDefaultFilter() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();
        List<Components> componentsList = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(componentsList::add);

        size = filterData(componentsList).size();

        return getSortedData(componentsList,size);
    }

    @GetMapping("/components/price-range/")
    public String getPriceRange() {
        Iterable<Components> componentsIterable = componentsRepository.findAll();
        List<Components> componentsList = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(componentsList::add);

        return componentsList.stream().min(Comparator.comparing(Components::getPrice)).get().getPrice() + ";" +
                componentsList.stream().max(Comparator.comparing(Components::getPrice)).get().getPrice();
    }

    @GetMapping("/components/size/")
    public long getDataSize() {
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

    public List<Components> getSortedData(List<Components> inputList, int size) {
        List<Components> componentsList = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                componentsList.add(inputList.get(i-1));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    componentsList.add(inputList.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    componentsList.add(inputList.get(i));
                }
            }
        }
        return componentsList;
    }

    public List<Components> filterData(List<Components> inputList) {
        return inputList.stream().filter(components ->
                components.getPrice() >= Integer.parseInt(filter.split(";")[0]) &&
                        components.getPrice() <= Integer.parseInt(filter.split(";")[1])).collect(Collectors.toList());

    }
}
