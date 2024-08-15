package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_users")

public class GymUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int gym;

    private String name;

    private String username;

    private String password;

    private String address;

    @CreationTimestamp
    private Timestamp created;
    @UpdateTimestamp
    private Timestamp updated;


    private Boolean active;
    private int profile;
    private String phone;
    private String email;

    public GymUsers(int id, int gym, String name, String username, String password, String address, Timestamp created, Timestamp updated, Boolean active, int profile, String phone, String email) {
        this.id = id;
        this.gym = gym;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.created = created;
        this.updated = updated;
        this.active = active;
        this.profile = profile;
        this.phone = phone;
        this.email = email;
    }

    public GymUsers() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGym() {
        return gym;
    }

    public void setGym(int gym) {
        this.gym = gym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
