package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_users")

public class GymUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_users_generator")
    @SequenceGenerator(name = "gym_users_generator", sequenceName = "gym_users_seq", allocationSize = 1)
    private int id;

    private int gym;
    private int pt;

    private String name;

    private String username;

    private String password;

    private String address;

    @CreationTimestamp
    private Timestamp created;
    @UpdateTimestamp
    private Timestamp updated;


    private int profile;
    private int addedby;
    private int updatedby;
    private int recentactivity;
    private int subscription;
    private int dietplan;
    private String phone;
    private String email;
    private String uniquetoken;
    private Boolean active;
    private Boolean weblog;
    private Boolean applog;
    private Boolean diet;


    public GymUsers(int id, int gym, int pt, String name, String username, String password, String address, Timestamp created, Timestamp updated, int profile, int addedby, int updatedby, int recentactivity, int subscription, int dietplan, String phone, String email, String uniquetoken, Boolean active, Boolean weblog, Boolean applog, Boolean diet) {
        this.id = id;
        this.gym = gym;
        this.pt = pt;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.created = created;
        this.updated = updated;
        this.profile = profile;
        this.addedby = addedby;
        this.updatedby = updatedby;
        this.recentactivity = recentactivity;
        this.subscription = subscription;
        this.dietplan = dietplan;
        this.phone = phone;
        this.email = email;
        this.uniquetoken = uniquetoken;
        this.active = active;
        this.weblog = weblog;
        this.applog = applog;
        this.diet = diet;
    }

    public GymUsers() {

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

    public int getPt() {
        return pt;
    }

    public void setPt(int pt) {
        this.pt = pt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
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

    public int getRecentactivity() {
        return recentactivity;
    }

    public void setRecentactivity(int recentactivity) {
        this.recentactivity = recentactivity;
    }

    public int getSubscription() {
        return subscription;
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public int getDietplan() {
        return dietplan;
    }

    public void setDietplan(int dietplan) {
        this.dietplan = dietplan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniquetoken() {
        return uniquetoken;
    }

    public void setUniquetoken(String uniquetoken) {
        this.uniquetoken = uniquetoken;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getWeblog() {
        return weblog;
    }

    public void setWeblog(Boolean weblog) {
        this.weblog = weblog;
    }

    public Boolean getApplog() {
        return applog;
    }

    public void setApplog(Boolean applog) {
        this.applog = applog;
    }

    public Boolean getDiet() {
        return diet;
    }

    public void setDiet(Boolean diet) {
        this.diet = diet;
    }
}
