package main.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class FilterController {

    private String filter;

    @PostMapping("/filter/")
    public void setFilter(@RequestHeader("filter") String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return this.filter;
    }
}
