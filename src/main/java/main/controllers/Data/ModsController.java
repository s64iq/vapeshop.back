package main.controllers.Data;

import main.model.Data.Impl.Mods;
import main.repository.datarepository.ModsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ModsController {

    @Autowired
    private ModsRepository modsRepository;

    private int page;

    private String filter;

    private int size;

    @GetMapping("/low-high/mods/")
    public List<Mods> getDataLowToHighFilter() {
        Iterable<Mods> modsIterable = modsRepository.findAll();
        List<Mods> modsList = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(modsList::add);

        modsList = filterData(modsList);
        modsList.sort(Comparator.comparing(Mods::getPrice));

        size = modsList.size();

        return getSortedData(modsList,size);
    }

    @GetMapping("/high-low/mods/")
    public List<Mods> getDataHighToLowFilter() {
        Iterable<Mods> modsIterable = modsRepository.findAll();
        List<Mods> modsList = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(modsList::add);

        modsList = filterData(modsList);
        modsList.sort(Comparator.comparing(Mods::getPrice).reversed());

        size = modsList.size();

        return getSortedData(modsList,size);
    }

    @GetMapping("/default/mods/")
    public List<Mods> getDataDefaultFilter() {
        Iterable<Mods> modsIterable = modsRepository.findAll();
        List<Mods> modsList = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(modsList::add);

        size = filterData(modsList).size();

        return getSortedData(modsList, size);
    }

    @GetMapping("/mods/price-range/")
    public String getPriceRange() {
        Iterable<Mods> modsIterable = modsRepository.findAll();
        List<Mods> modsList = new ArrayList<>();

        modsIterable.iterator().forEachRemaining(modsList::add);

        return modsList.stream().min(Comparator.comparing(Mods::getPrice)).get().getPrice() + ";" +
                modsList.stream().max(Comparator.comparing(Mods::getPrice)).get().getPrice();
    }

    @GetMapping("/mods/size/")
    public long getDataSize() {
        return size;
    }

    @PostMapping("/mods/page/")
    public void setCurrentPage(@RequestHeader("page") int page) {
        this.page = page * 100;
    }

    @PostMapping("/mods/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }

    public List<Mods> getSortedData(List<Mods> inputList, int size) {
        List<Mods> modsList = new ArrayList<>();
        if(page < size) {
            for(int i = page-99;i >= page-99&&i <= page;i++) {
                modsList.add(inputList.get(i-1));
            }
        } else {
            if(size-99 > 0) {
                for(int i = size-99;i >= size-99&&i < size;i++) {
                    modsList.add(inputList.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    modsList.add(inputList.get(i));
                }
            }
        }
        return modsList;
    }

    public List<Mods> filterData(List<Mods> inputList) {
        return inputList.stream().filter(mods ->
                mods.getPrice() >= Integer.parseInt(filter.split(";")[0]) &&
                        mods.getPrice() <= Integer.parseInt(filter.split(";")[1])).collect(Collectors.toList());

    }
}
