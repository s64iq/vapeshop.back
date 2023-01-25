package main.controllers.Data;

import main.controllers.FilterController;
import main.controllers.PageController;
import main.model.Data.Impl.Liquid;
import main.model.Data.Impl.Mod;
import main.repository.datarepository.ModRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ModController {

    @Autowired
    private ModRepository modRepository;

    @Autowired
    private PageController pageController;

    @Autowired
    private FilterController filterController;

    private int size;

    @GetMapping("/mods/product/")
    public Mod getProduct(@RequestHeader("productName") String productName) {
        return findProduct(productName);
    }

    @GetMapping("/mods/low-high/")
    public List<Mod> getDataLowToHighFilter() {
        Iterable<Mod> modsIterable = modRepository.findAll();
        List<Mod> modList = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(modList::add);

        modList = filterData(modList);
        modList.sort(Comparator.comparing(Mod::getPrice));

        size = modList.size();

        return getSortedData(modList,size);
    }

    @GetMapping("/mods/high-low/")
    public List<Mod> getDataHighToLowFilter() {
        Iterable<Mod> modsIterable = modRepository.findAll();
        List<Mod> modList = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(modList::add);

        modList = filterData(modList);
        modList.sort(Comparator.comparing(Mod::getPrice).reversed());

        size = modList.size();

        return getSortedData(modList,size);
    }

    @GetMapping("/mods/default/")
    public List<Mod> getDataDefaultFilter() {
        Iterable<Mod> modsIterable = modRepository.findAll();
        List<Mod> modList = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(modList::add);

        modList = filterData(modList);

        size = modList.size();

        return getSortedData(modList, size);
    }

    @GetMapping("/mods/price-range/")
    public String getPriceRange() {
        Iterable<Mod> modsIterable = modRepository.findAll();
        List<Mod> modList = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(modList::add);

        return modList.stream().min(Comparator.comparing(Mod::getPrice)).get().getPrice() + ";" +
                modList.stream().max(Comparator.comparing(Mod::getPrice)).get().getPrice();
    }

    @GetMapping("/mods/size/")
    public long getDataSize() {
        return size;
    }

    public List<Mod> getSortedData(List<Mod> inputList, int size) {
        List<Mod> modList = new ArrayList<>();
        if(pageController.getPage() < size) {
            for(int i = pageController.getPage()-99;i >= pageController.getPage()-99&&i <= pageController.getPage();i++) {
                modList.add(inputList.get(i-1));
            }
        } else {
            if(size-99 > 0) {
                for(int i = pageController.getPage()-99;i >= pageController.getPage()-99 && i < size;i++) {
                    modList.add(inputList.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    modList.add(inputList.get(i));
                }
            }
        }
        return modList;
    }

    public List<Mod> filterData(List<Mod> inputList) {
        return inputList.stream().filter(mod ->
                mod.getPrice() >= Integer.parseInt(filterController.getFilter().split(";")[0]) &&
                mod.getPrice() <= Integer.parseInt(filterController.getFilter().split(";")[1])).collect(Collectors.toList());

    }

    public Mod findProduct(String productName) {
        Iterable<Mod> modIterable = modRepository.findAll();
        for (Mod mod : modIterable) {
            if(mod.getUrl().indexOf(productName) != -1) {
                return mod;
            }
        }
        return null;
    }
}
