package main.repository;

import main.model.Order;
import main.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {
    Optional<List<Order>> findByUser(User user);

    boolean existsByUserAndProductName(User user, String productName);

    Order findByUserAndProductName(User user, String productName);


}
