package main.controllers.Data;

import main.controllers.FilterController;
import main.controllers.PageController;
import main.model.Data.Impl.Liquid;
import main.repository.datarepository.LiquidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class LiquidController {

    @Autowired
    private LiquidRepository liquidsRepository;

    @Autowired
    private PageController pageController;

    @Autowired
    private FilterController filterController;

    private int size;

    @GetMapping("/liquids/product/")
    public Liquid getProduct(@RequestHeader("productName") String productName) {
        return liquidsRepository.findByUrlContains(productName)
                .orElseThrow(() -> new RuntimeException("Error, Liquid not found"));
    }

    @GetMapping("/liquids/low-high/")
    public List<Liquid> getDataLowToHighFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> liquidList = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(liquidList::add);

        liquidList = filterData(liquidList);
        liquidList.sort(Comparator.comparing(Liquid::getPrice));

        size = liquidList.size();

        return getSortedData(liquidList,size);
    }

    @GetMapping("/liquids/high-low/")
    public List<Liquid> getDataHighToLowFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> liquidList = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(liquidList::add);

        liquidList = filterData(liquidList);
        liquidList.sort(Comparator.comparing(Liquid::getPrice).reversed());

        size = liquidList.size();

        return getSortedData(liquidList,size);
    }

    @GetMapping("/liquids/default/")
    public List<Liquid> getDataDefaultFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> liquidList = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(liquidList::add);

        liquidList = filterData(liquidList);

        size = liquidList.size();

        return getSortedData(liquidList, size);
    }

    @GetMapping("/liquids/price-range/")
    public String getPriceRange() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> liquidList = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(liquidList::add);

        return liquidList.stream().min(Comparator.comparing(Liquid::getPrice)).get().getPrice() + ";" +
               liquidList.stream().max(Comparator.comparing(Liquid::getPrice)).get().getPrice();
    }
    @GetMapping("/liquids/size/")
    public long getDataSize() {
        return size;
    }

    public List<Liquid> getSortedData(List<Liquid> inputList, int size) {
        List<Liquid> liquidList = new ArrayList<>();
        if(pageController.getPage() < size) {
            for(int i = pageController.getPage()-99;i >= pageController.getPage()-99 && i <= pageController.getPage();i++) {
                liquidList.add(inputList.get(i-1));
            }
        } else {
            if(size-99 > 0) {
                for(int i = pageController.getPage()-99;i >= pageController.getPage()-99 && i < size;i++) {
                    liquidList.add(inputList.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    liquidList.add(inputList.get(i));
                }
            }
        }
        return liquidList;
    }

    public List<Liquid> filterData(List<Liquid> inputList) {
        return inputList.stream().filter(liquid ->
                        liquid.getPrice() >= Integer.parseInt(filterController.getFilter().split(";")[0]) &&
                        liquid.getPrice() <= Integer.parseInt(filterController.getFilter().split(";")[1])).collect(Collectors.toList());

    }
}
