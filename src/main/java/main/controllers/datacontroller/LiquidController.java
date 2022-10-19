package main.controllers.datacontroller;

import main.model.data.liquid.Liquid;
import main.model.data.mods.Mods;
import main.model.data.vape.Vape;
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

    /*@GetMapping("/liquid/")
    public List<Liquid> list() {
        int i = 0;
        Iterable<Liquid> liquidIterable = liquidsRepository.findAll();
        List<Liquid> liquid = new ArrayList<>();
        for(Liquid liquid1 : liquidIterable) {
            i++;
            if(i >= page-99&&i <= page) {
                liquid.add(liquid1);
            }
        }
        return liquid;
    }*/

    @GetMapping("/low-high/liquids/")
    public List<Liquid> getDataInFilteredListByLowToHighAndSize() {
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
    public List<Liquid> getDataInFilteredListByHighToLowAndSize() {
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
    public List<Liquid> getDataInFilteredListByDefaultAndSize() {
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

    @GetMapping("/liquids/price-range/")
    public String getPriceRange(String priceFilterRange) {
        Iterable<Liquid> liquidsIterable = liquidsRepository.findAll();

        List<Liquid> unsortedLiquids = new ArrayList<>();
        for(Liquid liquid : liquidsIterable) {
            unsortedLiquids.add(liquid);
        }

        priceFilterRange = unsortedLiquids.stream().min(Comparator.comparing(Liquid::getPriceDouble)).get().getPriceDouble() + ";" +
                           unsortedLiquids.stream().max(Comparator.comparing(Liquid::getPriceDouble)).get().getPriceDouble();

        return priceFilterRange;
    }

    @GetMapping("/liquids/filter-low/length/")
    public long getSizeMinFilter() {
        return size;
    }

    @GetMapping("/liquids/filter-high/length/")
    public long getSizeMaxFilter() {
        return size;
    }

    @GetMapping("/liquids/filter-default/length/")
    public long getSizeDefaultFilter() {
        return size;
    }

    @PostMapping("/liquids/page/")
    public void setCurrentPage(@RequestHeader("page") int page) {
        this.page = page;
    }

    @PostMapping("/liquids/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }
}
