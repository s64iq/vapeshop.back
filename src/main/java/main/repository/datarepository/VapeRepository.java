package main.repository.datarepository;

import main.model.data.vape.Vape;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VapeRepository extends CrudRepository<Vape,Integer> {
}
