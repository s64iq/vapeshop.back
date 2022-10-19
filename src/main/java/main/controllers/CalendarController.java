package main.controllers;

import main.configs.jwt.JwtUtils;
import main.model.CalendarNoteEntity;
import main.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class CalendarController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CalendarRepository calendarRepository;

    @GetMapping("/calendar/")
    public List<CalendarNoteEntity> list(@RequestHeader("Authorization") String token) {
        String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]);
        Iterable<CalendarNoteEntity> calendarNoteIterable = calendarRepository.findAll();
        ArrayList<CalendarNoteEntity> calendarNotes = new ArrayList<>();
        for(CalendarNoteEntity calendarNoteEntity : calendarNoteIterable) {
            if(calendarNoteEntity.getUsername().equals(userNameFromJwtToken)) {
                calendarNotes.add(calendarNoteEntity);
            }
        }
        return calendarNotes;
    }

    @PostMapping("/calendar/")
    public int add(CalendarNoteEntity calendarNoteEntity) {
        CalendarNoteEntity newCalendarNoteEntity = calendarRepository.save(calendarNoteEntity);
        return newCalendarNoteEntity.getId();
    }

    /*@GetMapping("/calendar/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Optional<CalendarNoteEntity> optionalCalendarNote = calendarRepository.findById(id);
        if(!optionalCalendarNote.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalCalendarNote.get(), HttpStatus.OK);
    }*/

    @DeleteMapping("/calendar/{id}")
    public void delete(@PathVariable int id) {
        calendarRepository.deleteById(id);
    }

    /*@DeleteMapping("/calendar/")
    public void deleteAll() {
        calendarRepository.deleteAll();
    }*/
}
