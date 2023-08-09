package com.ashik.WhatsApp.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private String full_name;

    private String email;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id) && full_name.equals(user.full_name) && email.equals(user.email) && profile_picture.equals(user.profile_picture) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, full_name, email, profile_picture, password);
    }

    private String profile_picture;

    private String password;



}
