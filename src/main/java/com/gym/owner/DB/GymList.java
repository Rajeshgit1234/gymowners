package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_list")

public class GymList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_list_generator")
    @SequenceGenerator(name = "gym_list_generator", sequenceName = "gym_list_seq", allocationSize = 1)
    private int id;
    private int created_by;
    private String name;
    private String gym_name;
    private String adress;
    private String description;
    @CreationTimestamp
    private Timestamp created_on;
    @UpdateTimestamp
    private Timestamp updated_on;
    private boolean active;

    public GymList(int id, int created_by, String name, String gym_name, String adress, String description, Timestamp created_on, Timestamp updated_on, boolean active) {
        this.id = id;
        this.created_by = created_by;
        this.name = name;
        this.gym_name = gym_name;
        this.adress = adress;
        this.description = description;
        this.created_on = created_on;
        this.updated_on = updated_on;
        this.active = active;
    }

    public GymList() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGym_name() {
        return gym_name;
    }

    public void setGym_name(String gym_name) {
        this.gym_name = gym_name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
