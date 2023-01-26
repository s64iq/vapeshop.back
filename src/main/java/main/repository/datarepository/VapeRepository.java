package main.repository.datarepository;

import main.model.Data.Impl.Vape;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VapeRepository extends CrudRepository<Vape,Integer> {
    Optional<Vape> findByUrlContains(String url);
}
