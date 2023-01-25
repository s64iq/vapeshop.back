package main.controllers.Data;

import main.controllers.FilterController;
import main.controllers.PageController;
import main.model.Data.Impl.Mod;
import main.model.Data.Impl.Vape;
import main.repository.datarepository.VapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class VapeController {

    @Autowired
    private VapeRepository vapeRepository;

    @Autowired
    private PageController pageController;

    @Autowired
    private FilterController filterController;

    private int size;

    @GetMapping("/vapes/product/")
    public Vape getProduct(@RequestHeader("productName") String productName) {
        return findProduct(productName);
    }

    @GetMapping("/vapes/low-high/")
    public List<Vape> getDataLowToHighFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> vapeList = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(vapeList::add);

        vapeList = filterData(vapeList);
        vapeList.sort(Comparator.comparing(Vape::getPrice));

        size = vapeList.size();

        return getSortedData(vapeList,size);
    }

    @GetMapping("/vapes/high-low/")
    public List<Vape> getDataHighToLowFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> vapeList = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(vapeList::add);

        vapeList = filterData(vapeList);
        vapeList.sort(Comparator.comparing(Vape::getPrice).reversed());

        size = vapeList.size();

        return getSortedData(vapeList,size);
    }

    @GetMapping("/vapes/default/")
    public List<Vape> getDataDefaultFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> vapeList = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(vapeList::add);

        vapeList = filterData(vapeList);

        size = vapeList.size();

        return getSortedData(vapeList, size);
    }

    @GetMapping("/vapes/price-range/")
    public String getPriceRange() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> vapeList = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(vapeList::add);

        return vapeList.stream().min(Comparator.comparing(Vape::getPrice)).get().getPrice() + ";" +
               vapeList.stream().max(Comparator.comparing(Vape::getPrice)).get().getPrice();
    }

    @GetMapping("/vapes/size/")
    public long getDataSize() {
        return size;
    }

    public List<Vape> getSortedData(List<Vape> inputList, int size) {
        List<Vape> vapeList = new ArrayList<>();
        if(pageController.getPage() < size) {
            for(int i = pageController.getPage()-99;i >= pageController.getPage()-99 && i <= pageController.getPage();i++) {
                vapeList.add(inputList.get(i-1));
            }
        } else {
            if(size-99 > 0) {
                for(int i = pageController.getPage()-99;i >= pageController.getPage()-99 && i < size;i++) {
                    vapeList.add(inputList.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    vapeList.add(inputList.get(i));
                }
            }
        }
        return vapeList;
    }

    public List<Vape> filterData(List<Vape> inputList) {
        return inputList.stream().filter(vape ->
                vape.getPrice() >= Integer.parseInt(filterController.getFilter().split(";")[0]) &&
                vape.getPrice() <= Integer.parseInt(filterController.getFilter().split(";")[1])).collect(Collectors.toList());

    }

    public Vape findProduct(String productName) {
        Iterable<Vape> vapeIterable = vapeRepository.findAll();
        for (Vape vape : vapeIterable) {
            if(vape.getUrl().indexOf(productName) != -1) {
                return vape;
            }
        }
        return null;
    }
}
