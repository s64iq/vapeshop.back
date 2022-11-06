package main.controllers.Data;

import main.model.Data.Components;
import main.model.Data.Liquid;
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
        List<Liquid> unsortedLiquidArray = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(unsortedLiquidArray::add);

        if(filter != null) {
            unsortedLiquidArray = unsortedLiquidArray.stream().filter(liquid ->
                    liquid.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    liquid.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedLiquidArray.size();
        unsortedLiquidArray.sort(Comparator.comparing(Liquid::getPriceDouble));

        List<Liquid> liquidArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                liquidArray.add(unsortedLiquidArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    liquidArray.add(unsortedLiquidArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    liquidArray.add(unsortedLiquidArray.get(i));
                }
            }
        }

        return liquidArray;
    }

    @GetMapping("/high-low/liquids/")
    public List<Liquid> getDataHighToLowFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> unsortedLiquidArray = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(unsortedLiquidArray::add);

        if(filter != null) {
            unsortedLiquidArray = unsortedLiquidArray.stream().filter(liquid ->
                    liquid.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    liquid.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedLiquidArray.size();
        unsortedLiquidArray.sort(Comparator.comparing(Liquid::getPriceDouble).reversed());

        List<Liquid> liquidArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                liquidArray.add(unsortedLiquidArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    liquidArray.add(unsortedLiquidArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    liquidArray.add(unsortedLiquidArray.get(i));
                }
            }
        }

        return liquidArray;
    }

    @GetMapping("/default/liquids/")
    public List<Liquid> getDataDefaultFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> unsortedLiquidArray = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(unsortedLiquidArray::add);

        if(filter != null) {
            unsortedLiquidArray = unsortedLiquidArray.stream().filter(liquid ->
                    liquid.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    liquid.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedLiquidArray.size();

        List<Liquid> liquidArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                liquidArray.add(unsortedLiquidArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    liquidArray.add(unsortedLiquidArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    liquidArray.add(unsortedLiquidArray.get(i));
                }
            }
        }



        return liquidArray;
    }

    @GetMapping("/liquids/price-range/")
    public String getPriceRange() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();
        List<Liquid> unsortedLiquidArray = new ArrayList<>();

        liquidsIterable.iterator().forEachRemaining(unsortedLiquidArray::add);

        return unsortedLiquidArray.stream().min(Comparator.comparing(Liquid::getPriceDouble)).get().getPriceDouble() + ";" +
                unsortedLiquidArray.stream().max(Comparator.comparing(Liquid::getPriceDouble)).get().getPriceDouble();
    }

    @GetMapping("/liquids/filter-low/length/")
    public long getSizeLowFilter() {
        return size;
    }

    @GetMapping("/liquids/filter-high/length/")
    public long getSizeHighFilter() {
        return size;
    }

    @GetMapping("/liquids/filter-default/length/")
    public long getSizeDefaultFilter() {
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
}
