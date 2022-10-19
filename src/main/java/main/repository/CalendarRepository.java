package main.repository;

import main.model.CalendarNoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends CrudRepository<CalendarNoteEntity,Integer> {
}
