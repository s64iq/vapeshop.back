package main.model.Data.ProductTypes;

import main.model.Product;

public abstract class ProductWithFeatures implements Product {
    @Override
    public abstract int getId();

    @Override
    public abstract String getName();

    @Override
    public abstract int getPrice();

    @Override
    public abstract String getUrl();

    public abstract String getXark();

    public abstract String getComp();
}
