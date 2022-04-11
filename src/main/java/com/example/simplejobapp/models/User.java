package com.example.simplejobapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class User extends Model {
    @Column(unique = true)
    private String username;

    private String email;
    private String phone;

    private String password;

    @Transient
    private String token;

    @JsonIgnore
    public Collection<String> getRoles() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("user");
        return objects;
    }
}
