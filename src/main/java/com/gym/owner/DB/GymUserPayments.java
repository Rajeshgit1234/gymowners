package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "gym_user_payments")
public class GymUserPayments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_user_payments_generator")
    @SequenceGenerator(name = "gym_user_payments_generator", sequenceName = "gym_user_payments_seq", allocationSize = 1)
    private int id;
    private int gym;
    private int customer;
    private int addedby;
    @Column(name = "updatedby")
    private int updatedby;
    @Column(name = "paymonth")
    private int paymonth;
    @Column(name = "payyear")
    private int payyear;
    private float amount;
    private float finalamount;
    @CreationTimestamp
    private Timestamp createdon;
    @UpdateTimestamp
    private Timestamp updatedon;
    private String description;
    private boolean status;
    private boolean discountadded;
    private int subscription;
    private int fromdoy;
    private int todoy;
    private Date frompaydate;
    private Date topaydate;

    public GymUserPayments(int id, int gym, int customer, int addedby, int updatedby, int paymonth, int payyear, float amount, float finalamount, Timestamp createdon, Timestamp updatedon, String description, boolean status, boolean discountadded, int subscription, int fromdoy, int todoy, Date frompaydate, Date topaydate) {
        this.id = id;
        this.gym = gym;
        this.customer = customer;
        this.addedby = addedby;
        this.updatedby = updatedby;
        this.paymonth = paymonth;
        this.payyear = payyear;
        this.amount = amount;
        this.finalamount = finalamount;
        this.createdon = createdon;
        this.updatedon = updatedon;
        this.description = description;
        this.status = status;
        this.discountadded = discountadded;
        this.subscription = subscription;
        this.fromdoy = fromdoy;
        this.todoy = todoy;
        this.frompaydate = frompaydate;
        this.topaydate = topaydate;
    }

    public GymUserPayments() {

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

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
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

    public int getPaymonth() {
        return paymonth;
    }

    public void setPaymonth(int paymonth) {
        this.paymonth = paymonth;
    }

    public int getPayyear() {
        return payyear;
    }

    public void setPayyear(int payyear) {
        this.payyear = payyear;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getFinalamount() {
        return finalamount;
    }

    public void setFinalamount(float finalamount) {
        this.finalamount = finalamount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDiscountadded() {
        return discountadded;
    }

    public void setDiscountadded(boolean discountadded) {
        this.discountadded = discountadded;
    }

    public int getSubscription() {
        return subscription;
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public int getFromdoy() {
        return fromdoy;
    }

    public void setFromdoy(int fromdoy) {
        this.fromdoy = fromdoy;
    }

    public int getTodoy() {
        return todoy;
    }

    public void setTodoy(int todoy) {
        this.todoy = todoy;
    }

    public Date getFrompaydate() {
        return frompaydate;
    }

    public void setFrompaydate(Date frompaydate) {
        this.frompaydate = frompaydate;
    }

    public Date getTopaydate() {
        return topaydate;
    }

    public void setTopaydate(Date topaydate) {
        this.topaydate = topaydate;
    }
}
