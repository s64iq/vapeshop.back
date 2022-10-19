package main.repository;

import main.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository  extends CrudRepository<Order,Integer> {
}
