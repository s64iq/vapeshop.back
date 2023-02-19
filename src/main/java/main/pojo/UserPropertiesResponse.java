package main.pojo;

import lombok.Getter;
import lombok.Setter;
import main.model.Role;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserPropertiesResponse {
    private int id;

    private String username;

    private String email;

    private Set<Role> roles = new HashSet<>();

    public UserPropertiesResponse(int id, String username, String email, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
