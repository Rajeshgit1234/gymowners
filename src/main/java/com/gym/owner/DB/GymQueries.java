package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_queries")
public class GymQueries {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_queries_generator")
    @SequenceGenerator(name = "gym_queries_generator", sequenceName = "gym_queries_seq", allocationSize = 1)
    private int id;

    private String query;
    private int phone;
    private int gymid;
    private int added;
    private int updated;
    @CreationTimestamp
    private Timestamp createdon;
    @UpdateTimestamp
    private Timestamp updatedon;
    private Boolean status;

    public GymQueries(int id, String query, int phone, int gymid, int added, int updated, Timestamp createdon, Timestamp updatedon, Boolean status) {
        this.id = id;
        this.query = query;
        this.phone = phone;
        this.gymid = gymid;
        this.added = added;
        this.updated = updated;
        this.createdon = createdon;
        this.updatedon = updatedon;
        this.status = status;
    }

    public GymQueries() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getGymid() {
        return gymid;
    }

    public void setGymid(int gymid) {
        this.gymid = gymid;
    }

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public Timestamp getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Timestamp createdon) {
        this.createdon = createdon;
    }

    public Timestamp getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(Timestamp updatedon) {
        this.updatedon = updatedon;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
