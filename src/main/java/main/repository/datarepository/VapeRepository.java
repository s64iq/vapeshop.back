package main.repository.datarepository;

import main.model.Data.Vape;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VapeRepository extends CrudRepository<Vape,Integer> {
}
