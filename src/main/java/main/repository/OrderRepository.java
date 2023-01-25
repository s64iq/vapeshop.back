package main.repository;

import main.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {

    boolean existsByUsernameContainsAndProductnameContains(String username, String productname);

    Optional<Order> findByUsernameContainsAndProductnameContains(String username, String productname);

    List<Order> findByUsername(String username);
}
