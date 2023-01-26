package main.controllers.Data;

import main.controllers.FilterController;
import main.controllers.PageController;
import main.model.Data.Impl.Component;
import main.pojo.MessageResponse;
import main.repository.datarepository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ComponentController {
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private PageController pageController;

    @Autowired
    private FilterController filterController;

    private int size;

    @GetMapping("/components/product/")
    public Component getProduct(@RequestHeader("productName") String productName) {
        return componentRepository.findByUrlContains(productName)
                .orElseThrow(() -> new RuntimeException("Error, Component not found"));
    }

    @GetMapping("/components/low-high/")
    public List<Component> getDataLowToHighFilter() {
        Iterable<Component> componentsIterable = componentRepository.findAll();
        List<Component> componentList = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(componentList::add);

        componentList = filterData(componentList);
        componentList.sort(Comparator.comparing(Component::getPrice));

        size = componentList.size();

        return getSortedData(componentList,size);
    }

    @GetMapping("/components/high-low/")
    public List<Component> getDataHighToLowFilter() {
        Iterable<Component> componentsIterable = componentRepository.findAll();
        List<Component> componentList = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(componentList::add);

        componentList = filterData(componentList);
        componentList.sort(Comparator.comparing(Component::getPrice).reversed());

        size = componentList.size();

        return getSortedData(componentList,size);
    }

    @GetMapping("/components/default/")
    public List<Component> getDataDefaultFilter() {
        Iterable<Component> componentsIterable = componentRepository.findAll();
        List<Component> componentList = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(componentList::add);

        componentList = filterData(componentList);

        size = componentList.size();

        return getSortedData(componentList,size);
    }

    @GetMapping("/components/price-range/")
    public String getPriceRange() {
        Iterable<Component> componentsIterable = componentRepository.findAll();
        List<Component> componentList = new ArrayList<>();

        componentsIterable.iterator().forEachRemaining(componentList::add);

        return componentList.stream().min(Comparator.comparing(Component::getPrice)).get().getPrice() + ";" +
                componentList.stream().max(Comparator.comparing(Component::getPrice)).get().getPrice();
    }

    @GetMapping("/components/size/")
    public long getDataSize() {
        return size;
    }

    public List<Component> getSortedData(List<Component> inputList, int size) {
        List<Component> componentList = new ArrayList<>();
        if(pageController.getPage() < size) {
            for(int i = pageController.getPage()-99;i >= pageController.getPage()-99 && i <= pageController.getPage();i++) {
                componentList.add(inputList.get(i-1));
            }
        } else {
            if(size-99 > 0) {
                for(int i = pageController.getPage()-99;i >= pageController.getPage()-99 && i < size;i++) {
                    componentList.add(inputList.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    componentList.add(inputList.get(i));
                }
            }
        }
        return componentList;
    }

    public List<Component> filterData(List<Component> inputList) {
        return inputList.stream().filter(component ->
                component.getPrice() >= Integer.parseInt(filterController.getFilter().split(";")[0]) &&
                component.getPrice() <= Integer.parseInt(filterController.getFilter().split(";")[1])).collect(Collectors.toList());

    }
}
