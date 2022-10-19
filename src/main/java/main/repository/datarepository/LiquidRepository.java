package main.repository.datarepository;

import main.model.data.liquid.Liquid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquidRepository extends CrudRepository<Liquid,Integer> {
}
