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

    private int gym_id;

    private String name;

    private String username;

    private String password;

    private String address;

    @CreationTimestamp
    private Timestamp created;
    @UpdateTimestamp
    private Timestamp updated;


    private Boolean active;
    private int profile_id;

    public GymUsers(int id, int gym_id, String name, String username, String password, String address, Timestamp created, Timestamp updated, Boolean active, int profile_id) {
        this.id = id;
        this.gym_id = gym_id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.created = created;
        this.updated = updated;
        this.active = active;
        this.profile_id = profile_id;
    }

    public GymUsers() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGym_id() {
        return gym_id;
    }

    public void setGym_id(int gym_id) {
        this.gym_id = gym_id;
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

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }
}
