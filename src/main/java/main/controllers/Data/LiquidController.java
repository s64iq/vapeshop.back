package main.controllers.Data;

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

    private int page;

    private String filter;

    private int size;

    @GetMapping("/low-high/liquids/")
    public List<Liquid> getDataLowToHighFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> liquidList = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(liquidList::add);

        liquidList = filterData(liquidList);
        liquidList.sort(Comparator.comparing(Liquid::getPrice));

        size = liquidList.size();

        return getSortedData(liquidList,size);
    }

    @GetMapping("/high-low/liquids/")
    public List<Liquid> getDataHighToLowFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> liquidList = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(liquidList::add);

        liquidList = filterData(liquidList);
        liquidList.sort(Comparator.comparing(Liquid::getPrice).reversed());

        size = liquidList.size();

        return getSortedData(liquidList,size);
    }

    @GetMapping("/default/liquids/")
    public List<Liquid> getDataDefaultFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> liquidList = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(liquidList::add);

        size = filterData(liquidList).size();

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

    @PostMapping("/liquids/page/")
    public void setPage(@RequestHeader("page") int page) {
        this.page = page * 100;
    }

    @PostMapping("/liquids/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }

    public List<Liquid> getSortedData(List<Liquid> inputList, int size) {
        List<Liquid> liquidList = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                liquidList.add(inputList.get(i-1));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
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
                        liquid.getPrice() >= Integer.parseInt(filter.split(";")[0]) &&
                        liquid.getPrice() <= Integer.parseInt(filter.split(";")[1])).collect(Collectors.toList());

    }
}
