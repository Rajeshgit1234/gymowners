package com.gym.owner.DB;

import jakarta.persistence.*;

@Entity
@Table(name = "gym_expense_master")
public class ExpenseMaster {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String  expense_item;

    public ExpenseMaster(int id, String expense_item, boolean status) {
        this.id = id;
        this.expense_item = expense_item;
        this.status = status;
    }

    public ExpenseMaster() {

    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getExpense_item() {
        return expense_item;
    }

    public void setExpense_item(String expense_item) {
        this.expense_item = expense_item;
    }

    private boolean  status;
}
