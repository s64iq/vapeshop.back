package main.repository.datarepository;

import main.model.Data.Impl.Mod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModRepository extends CrudRepository<Mod,Integer> {
}
