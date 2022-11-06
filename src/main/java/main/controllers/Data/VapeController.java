package main.controllers.Data;

import main.model.Data.Vape;
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
    private int page;
    private String filter;
    private int size;

    @GetMapping("/low-high/vapes/")
    public List<Vape> getDataLowToHighFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> unsortedVapeArray = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(unsortedVapeArray::add);

        if(filter != null) {
            unsortedVapeArray = unsortedVapeArray.stream().filter(component ->
                    component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedVapeArray.size();
        unsortedVapeArray.sort(Comparator.comparing(Vape::getPriceDouble));

        List<Vape> vapeArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                vapeArray.add(unsortedVapeArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    vapeArray.add(unsortedVapeArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    vapeArray.add(unsortedVapeArray.get(i));
                }
            }
        }

        return vapeArray;
    }

    @GetMapping("/high-low/vapes/")
    public List<Vape> getDataHighToLowFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> unsortedVapeArray = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(unsortedVapeArray::add);

        if(filter != null) {
            unsortedVapeArray = unsortedVapeArray.stream().filter(component ->
                    component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedVapeArray.size();
        unsortedVapeArray.sort(Comparator.comparing(Vape::getPriceDouble).reversed());

        List<Vape> vapeArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                vapeArray.add(unsortedVapeArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    vapeArray.add(unsortedVapeArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    vapeArray.add(unsortedVapeArray.get(i));
                }
            }
        }

        return vapeArray;
    }

    @GetMapping("/default/vapes/")
    public List<Vape> getDataDefaultFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> unsortedVapeArray = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(unsortedVapeArray::add);

        if(filter != null) {
            unsortedVapeArray = unsortedVapeArray.stream().filter(component ->
                    component.getPriceDouble() >= Double.parseDouble(filter.split(";")[0]) &&
                    component.getPriceDouble() <= Double.parseDouble(filter.split(";")[1])).collect(Collectors.toList());
        }

        size = unsortedVapeArray.size();

        List<Vape> vapeArray = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                vapeArray.add(unsortedVapeArray.get(i));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    vapeArray.add(unsortedVapeArray.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    vapeArray.add(unsortedVapeArray.get(i));
                }
            }
        }

        return vapeArray;
    }

    @GetMapping("/vapes/price-range/")
    public String getPriceRange() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> unsortedVapeArray = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(unsortedVapeArray::add);

        return unsortedVapeArray.stream().min(Comparator.comparing(Vape::getPriceDouble)).get().getPriceDouble() + ";" +
                unsortedVapeArray.stream().max(Comparator.comparing(Vape::getPriceDouble)).get().getPriceDouble();
    }

    @GetMapping("/vapes/filter-low/length/")
    public long getSizeMinFilter() {
        return size;
    }

    @GetMapping("/vapes/filter-high/length/")
    public long getSizeMaxFilter() {
        return size;
    }

    @GetMapping("/vapes/filter-default/length/")
    public long getSizeDefaultFilter() {
        return size;
    }

    @PostMapping("/vapes/page/")
    public void setCurrentPage(@RequestHeader("page") int page) {
        this.page = page * 100;
    }

    @PostMapping("/vapes/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }
}
