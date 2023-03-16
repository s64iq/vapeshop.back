package main.repository.datarepository;

import main.model.Data.Impl.Mod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModRepository extends CrudRepository<Mod,Integer> {
    Optional<Mod> findByUrlContains(String url);

    Mod findByIdAllIgnoreCase(int id);
}
