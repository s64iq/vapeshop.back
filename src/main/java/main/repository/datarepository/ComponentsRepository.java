package main.repository.datarepository;

import main.model.Data.Components;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentsRepository extends CrudRepository<Components,Integer> {
}
