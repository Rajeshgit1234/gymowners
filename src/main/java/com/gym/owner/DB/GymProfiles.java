package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_profiles")
public class GymProfiles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_profiles_generator")
    @SequenceGenerator(name = "gym_profiles_generator", sequenceName = "gym_profiles_seq", allocationSize = 1)
    private int id;



    private int gym_id;
    private String profile_name;
    @CreationTimestamp
    private Timestamp created_at;
    @UpdateTimestamp
    private Timestamp updated_at;
    private boolean status;


    public GymProfiles(int id,int gym_id, String profile_name, Timestamp created_at, Timestamp updated_at, boolean status) {
        this.id = id;
        this.gym_id = gym_id;
        this.profile_name = profile_name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
    }

    public GymProfiles() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public int getGym_id() {
        return gym_id;
    }

    public void setGym_id(int gym_id) {
        this.gym_id = gym_id;
    }
}
