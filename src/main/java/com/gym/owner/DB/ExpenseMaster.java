package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "expense_master")
public class ExpenseMaster {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int added;
    private String  expense_item;

    private boolean  status;
    private int gym_id;
    @CreationTimestamp
    private Timestamp addedon;
    @UpdateTimestamp
    private Timestamp updateon;
    private int updatedby;


    public ExpenseMaster(int id, int added, String expense_item, boolean status, int gym_id, Timestamp addedon, Timestamp updateon, int updatedby) {
        this.id = id;
        this.added = added;
        this.expense_item = expense_item;
        this.status = status;
        this.gym_id = gym_id;
        this.addedon = addedon;
        this.updateon = updateon;
        this.updatedby = updatedby;
    }

    public ExpenseMaster() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
    }

    public String getExpense_item() {
        return expense_item;
    }

    public void setExpense_item(String expense_item) {
        this.expense_item = expense_item;
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

    public Timestamp getAddedon() {
        return addedon;
    }

    public void setAddedon(Timestamp addedon) {
        this.addedon = addedon;
    }

    public Timestamp getUpdateon() {
        return updateon;
    }

    public void setUpdateon(Timestamp updateon) {
        this.updateon = updateon;
    }

    public int getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(int updatedby) {
        this.updatedby = updatedby;
    }
}
