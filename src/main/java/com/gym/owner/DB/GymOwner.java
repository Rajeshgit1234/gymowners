package com.gym.owner.DB;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "gym_owners")

public class GymOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int gym_id;

    private String name;

    private String username;

    private String password;

    private String adress;

    private Boolean active;

    public GymOwner(int id, int gym_id, String name, String username, String password, String adress, Boolean active) {
        this.id = id;
        this.gym_id = gym_id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.adress = adress;
        this.active = active;
    }

    public GymOwner() {

    }

    public int getUser_id() {
        return id;
    }

    public void setUser_id(int user_id) {
        this.id = user_id;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
