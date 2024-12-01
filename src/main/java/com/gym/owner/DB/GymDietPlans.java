package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "gym_diet_plans")
public class GymDietPlans {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_diet_plans_generator")
    @SequenceGenerator(name = "gym_diet_plans_generator", sequenceName = "gym_diet_plans_seq", allocationSize = 1)
    private int id;
    private int gymid;
    private String diet_details;
    private String diet_name;
    private int addedby;
    private int updatedby;
    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;
    private boolean status;


    public GymDietPlans(int id, int gymid, String diet_details, String diet_name, int addedby, int updatedby, Date created, Date updated, boolean status) {
        this.id = id;
        this.gymid = gymid;
        this.diet_details = diet_details;
        this.diet_name = diet_name;
        this.addedby = addedby;
        this.updatedby = updatedby;
        this.created = created;
        this.updated = updated;
        this.status = status;
    }

    public GymDietPlans() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGymid() {
        return gymid;
    }

    public void setGymid(int gymid) {
        this.gymid = gymid;
    }

    public String getDiet_details() {
        return diet_details;
    }

    public void setDiet_details(String diet_details) {
        this.diet_details = diet_details;
    }

    public String getDiet_name() {
        return diet_name;
    }

    public void setDiet_name(String diet_name) {
        this.diet_name = diet_name;
    }

    public int getAddedby() {
        return addedby;
    }

    public void setAddedby(int addedby) {
        this.addedby = addedby;
    }

    public int getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(int updatedby) {
        this.updatedby = updatedby;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
