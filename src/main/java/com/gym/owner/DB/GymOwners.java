package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_owners")
public class GymOwners {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int gymid;
    private int owner;
    private boolean active;
    @CreationTimestamp
    private Timestamp createdon;
    @UpdateTimestamp
    private Timestamp updatedon;
    private int addedby;
    private int updatedby;



    public GymOwners(int id, int gymid, int owner, boolean active, Timestamp createdon, Timestamp updatedon, int addedby, int updatedby) {
        this.id = id;
        this.gymid = gymid;
        this.owner = owner;
        this.active = active;
        this.createdon = createdon;
        this.updatedon = updatedon;
        this.addedby = addedby;
        this.updatedby = updatedby;
    }

    public GymOwners() {

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

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
}
