package main.service;

import main.controllers.FilterController;
import main.controllers.PageController;
import main.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private PageController pageController;

    @Autowired
    private FilterController filterController;

    private int size;

    public List<Product> getDataLowToHighFilter(Iterable<? extends Product> products) {
        List<Product> productList = new ArrayList<>();

        products.iterator().forEachRemaining(productList::add);

        productList = filterData(productList);
        productList.sort(Comparator.comparing(Product::getPrice));

        size = productList.size();

        return getSortedData(productList,size);
    }

    public List<Product> getDataHighToLowFilter(Iterable<? extends Product> products) {
        List<Product> productList = new ArrayList<>();

        products.iterator().forEachRemaining(productList::add);

        productList = filterData(productList);
        productList.sort(Comparator.comparing(Product::getPrice).reversed());

        size = productList.size();

        return getSortedData(productList,size);
    }

    public List<Product> getDataDefaultFilter(Iterable<? extends Product> products) {
        List<Product> productList = new ArrayList<>();

        products.iterator().forEachRemaining(productList::add);

        productList = filterData(productList);

        size = productList.size();

        return getSortedData(productList,size);
    }

    public String getPriceRange(Iterable<? extends Product> products) {
        List<Product> productList = new ArrayList<>();

        products.iterator().forEachRemaining(productList::add);

        return productList.stream().min(Comparator.comparing(Product::getPrice)).get().getPrice() + ";" +
                productList.stream().max(Comparator.comparing(Product::getPrice)).get().getPrice();
    }

    public long getDataSize() {
        return size;
    }

    public List<Product> getSortedData(List<Product> inputList, int size) {
        List<Product> productList = new ArrayList<>();
        if(pageController.getPage() < size) {
            for(int i = pageController.getPage()-99;i >= pageController.getPage()-99 && i <= pageController.getPage();i++) {
                productList.add(inputList.get(i-1));
            }
        } else {
            if(size-99 > 0) {
                for(int i = pageController.getPage()-99;i >= pageController.getPage()-99 && i < size;i++) {
                    productList.add(inputList.get(i));
                }
            } else {
                for(int i = 0;i >= 0&&i < size;i++) {
                    productList.add(inputList.get(i));
                }
            }
        }
        return productList;
    }

    public List<Product> filterData(List<Product> inputList) {
        return inputList.stream().filter(product ->
                product.getPrice() >= Integer.parseInt(filterController.getFilter().split(";")[0]) &&
                        product.getPrice() <= Integer.parseInt(filterController.getFilter().split(";")[1])).collect(Collectors.toList());
    }
}
