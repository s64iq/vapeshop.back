package main.repository.datarepository;

import main.model.Data.Impl.Component;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ComponentRepository extends CrudRepository<Component,Integer> {
    Optional<Component> findByUrlContains(String url);


}
