package main.repository.datarepository;

import main.model.Data.Mods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModsRepository extends CrudRepository<Mods,Integer> {
}
