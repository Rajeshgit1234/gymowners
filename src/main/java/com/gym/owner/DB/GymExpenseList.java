package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_expense_list")

public class GymExpenseList {

    public GymExpenseList() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExp_id() {
        return exp_id;
    }

    public void setExp_id(int exp_id) {
        this.exp_id = exp_id;
    }

    public String getExp_remarks() {
        return exp_remarks;
    }

    public void setExp_remarks(String exp_remarks) {
        this.exp_remarks = exp_remarks;
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

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
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

    public GymExpenseList(int id, int exp_id, String exp_remarks, Timestamp created_on, Timestamp updated_on, int created_by, int updated_by, float amount, boolean status, int gym_id) {
        this.id = id;
        this.exp_id = exp_id;
        this.exp_remarks = exp_remarks;
        this.created_on = created_on;
        this.updated_on = updated_on;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.amount = amount;
        this.status = status;
        this.gym_id = gym_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int exp_id;
    private String  exp_remarks;
    //@CreationTimestamp
    private Timestamp created_on;
    //@UpdateTimestamp
    private Timestamp updated_on;
    private int created_by;
    private int updated_by;
    private float amount;
    private boolean  status;
    private int gym_id;
}
