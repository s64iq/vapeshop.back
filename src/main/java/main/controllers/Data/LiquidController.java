package main.controllers.Data;

import main.model.Data.Liquid;
import main.repository.datarepository.LiquidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        List<Liquid> unsortedLiquids = new ArrayList<>();
        for(Liquid liquid : liquidsIterable) {
            if(filter != null &&liquid.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&liquid.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedLiquids.add(liquid);
            }
            if(filter == null) {
                unsortedLiquids.add(liquid);
            }
        }
        size = unsortedLiquids.size();
        unsortedLiquids.sort(Comparator.comparing(Liquid::getPriceDouble));

        List<Liquid> liquids = new ArrayList<>();
        int i = 0;
        for(Liquid liquid : unsortedLiquids) {
            i++;
            if(i >= page-99&&i <= page) {
                liquids.add(liquid);
            }
        }
        return liquids;
    }

    @GetMapping("/high-low/liquids/")
    public List<Liquid> getDataHighToLowFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();

        List<Liquid> unsortedLiquids = new ArrayList<>();
        for(Liquid liquid : liquidsIterable) {
            if(filter != null &&liquid.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                    &&liquid.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedLiquids.add(liquid);
            }
            if(filter == null) {
                unsortedLiquids.add(liquid);
            }
        }
        size = unsortedLiquids.size();
        unsortedLiquids.sort(Comparator.comparing(Liquid::getPriceDouble).reversed());

        List<Liquid> liquids = new ArrayList<>();
        int i = 0;
        for(Liquid liquid : unsortedLiquids) {
            i++;
            if(i >= page-99&&i <= page) {
                liquids.add(liquid);
            }
        }
        return liquids;
    }

    @GetMapping("/default/liquids/")
    public List<Liquid> getDataDefaultFilter() {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();

        List<Liquid> unsortedLiquidArray = new ArrayList<>();
        for(Liquid liquid : liquidsIterable) {
            if(filter != null &&liquid.getPriceDouble() >= Double.parseDouble(filter.split(";")[0])
                              &&liquid.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])) {
                unsortedLiquidArray.add(liquid);
            }
            if(filter == null) {
                unsortedLiquidArray.add(liquid);
            }
        }
        size = unsortedLiquidArray.size();

        List<Liquid> liquidArray = new ArrayList<>();
        int i = 0;
        for(Liquid liquid : unsortedLiquidArray) {
            i++;
            if(i >= page-99&&i <= page) {
                liquidArray.add(liquid);
            }
        }
        return liquidArray;
    }

    @GetMapping("/liquids/price-range/")
    public String getPriceRange(String priceRange) {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();

        List<Liquid> unsortedLiquidArray = new ArrayList<>();
        for(Liquid liquid : liquidsIterable) {
            unsortedLiquidArray.add(liquid);
        }

        priceRange = unsortedLiquidArray.stream().min(Comparator.comparing(Liquid::getPriceDouble)).get().getPriceDouble() + ";" +
                     unsortedLiquidArray.stream().max(Comparator.comparing(Liquid::getPriceDouble)).get().getPriceDouble();

        return priceRange;
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
        this.page = page;
    }

    @PostMapping("/liquids/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }
}
