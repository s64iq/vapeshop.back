package main.repository.datarepository;

import main.model.Data.Impl.Liquid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiquidRepository extends CrudRepository<Liquid,Integer> {
    Optional<Liquid> findByUrlContains(String url);

}
