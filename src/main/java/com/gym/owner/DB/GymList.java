package com.gym.owner.DB;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_list")

public class GymList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;



    private String name;
    private String description;
    private Timestamp created_on;
    private Timestamp updated_on;
    private boolean status;

    public GymList(int id, String name, String description, Timestamp created_on, Timestamp updated_on, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created_on = created_on;
        this.updated_on = updated_on;
        this.status = status;
    }

    public GymList() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public Timestamp getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Timestamp updated_on) {
        this.updated_on = updated_on;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
