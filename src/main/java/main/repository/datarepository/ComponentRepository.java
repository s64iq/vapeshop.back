package main.repository.datarepository;

import main.model.Data.Impl.Component;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ComponentRepository extends CrudRepository<Component,Integer> {
    Component findByName(String name);


}
