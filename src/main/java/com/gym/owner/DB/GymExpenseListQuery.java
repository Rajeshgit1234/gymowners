package com.gym.owner.DB;

import jakarta.persistence.*;

import java.sql.Timestamp;
@Entity
public class GymExpenseListQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_expense_list_query_generator")
    @SequenceGenerator(name = "gym_expense_list_query_generator", sequenceName = "gym_expense_list_query_seq", allocationSize = 1)
    private int id;
    private String name;
    private Timestamp created_on;
    private float amount;
    private int exp_id;
    private String  expense_item;
    private String  exp_remarks;

    public GymExpenseListQuery(int id, String name, Timestamp created_on, float amount, int exp_id, String expense_item, String exp_remarks) {
        this.id = id;
        this.name = name;
        this.created_on = created_on;
        this.amount = amount;
        this.exp_id = exp_id;
        this.expense_item = expense_item;
        this.exp_remarks = exp_remarks;
    }

    public GymExpenseListQuery() {

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

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getExp_id() {
        return exp_id;
    }

    public void setExp_id(int exp_id) {
        this.exp_id = exp_id;
    }

    public String getExpense_item() {
        return expense_item;
    }

    public void setExpense_item(String expense_item) {
        this.expense_item = expense_item;
    }

    public String getExp_remarks() {
        return exp_remarks;
    }

    public void setExp_remarks(String exp_remarks) {
        this.exp_remarks = exp_remarks;
    }
}
