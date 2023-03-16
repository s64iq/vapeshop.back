package main.repository;

import main.model.EProduct;
import main.model.Order;
import main.model.ProductType;
import main.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {
    boolean existsByUserAndProductIdAndProductType(User user, int productId, ProductType productType);

    List<Order> findByUser(User user);

    Order findByUserAndProductIdAndProductType(User user, int productId, ProductType productType);
}
