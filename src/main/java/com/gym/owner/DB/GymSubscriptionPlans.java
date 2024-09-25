package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_subscription_plans")
public class GymSubscriptionPlans {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_subscription_plans_generator")
    @SequenceGenerator(name = "gym_subscription_plans_generator", sequenceName = "gym_subscription_plans_seq", allocationSize = 1)

    private int id;
    private int gym;
    private String description;
    private int amount;
    private int added;
    private int updated;
    @CreationTimestamp
    private Timestamp createdon;
    @UpdateTimestamp
    private Timestamp updatedon;
    private Boolean status;


    public GymSubscriptionPlans(int id, int gym, String description, int amount, int added, int updated, Timestamp createdon, Timestamp updatedon, Boolean status) {
        this.id = id;
        this.gym = gym;
        this.description = description;
        this.amount = amount;
        this.added = added;
        this.updated = updated;
        this.createdon = createdon;
        this.updatedon = updatedon;
        this.status = status;
    }

    public GymSubscriptionPlans() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
