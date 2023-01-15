package main.controllers.Data;

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
    private int page;
    private String filter;
    private int size;

    @GetMapping("/low-high/vapes/")
    public List<Vape> getDataLowToHighFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> vapeList = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(vapeList::add);

        vapeList = filterData(vapeList);
        vapeList.sort(Comparator.comparing(Vape::getPrice));

        size = vapeList.size();

        return getSortedData(vapeList,size);
    }

    @GetMapping("/high-low/vapes/")
    public List<Vape> getDataHighToLowFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> vapeList = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(vapeList::add);

        vapeList = filterData(vapeList);
        vapeList.sort(Comparator.comparing(Vape::getPrice).reversed());

        size = vapeList.size();

        return getSortedData(vapeList,size);
    }

    @GetMapping("/default/vapes/")
    public List<Vape> getDataDefaultFilter() {
        Iterable<Vape> vapesIterable = vapeRepository.findAll();
        List<Vape> vapeList = new ArrayList<>();

        vapesIterable.iterator().forEachRemaining(vapeList::add);

        size = filterData(vapeList).size();

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

    @PostMapping("/vapes/page/")
    public void setCurrentPage(@RequestHeader("page") int page) {
        this.page = page * 100;
    }

    @PostMapping("/vapes/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }

    public List<Vape> getSortedData(List<Vape> inputList, int size) {
        List<Vape> vapeList = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                vapeList.add(inputList.get(i-1));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
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
                vape.getPrice() >= Integer.parseInt(filter.split(";")[0]) &&
                        vape.getPrice() <= Integer.parseInt(filter.split(";")[1])).collect(Collectors.toList());

    }
}
