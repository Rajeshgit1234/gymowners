package com.gym.owner.DB;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "gym_attendance")
public class GymAttendance {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_attendance_generator")
    @SequenceGenerator(name = "gym_attendance_generator", sequenceName = "gym_attendance_seq", allocationSize = 1)
    private int id;
    private int gymid;
    private int userid;
    private int doy;
    private int year;
    private int fromhour;
    private int tohour;
    private int added;
    private int updated;
    @CreationTimestamp
    private Timestamp createdon;
    @UpdateTimestamp
    private Timestamp updatedon;
    private Boolean status;
    private String datedoy;

    public GymAttendance(int id, int gymid, int userid, int doy, int year, int fromhour, int tohour, int added, int updated, Timestamp createdon, Timestamp updatedon, Boolean status, String datedoy) {
        this.id = id;
        this.gymid = gymid;
        this.userid = userid;
        this.doy = doy;
        this.year = year;
        this.fromhour = fromhour;
        this.tohour = tohour;
        this.added = added;
        this.updated = updated;
        this.createdon = createdon;
        this.updatedon = updatedon;
        this.status = status;
        this.datedoy = datedoy;
    }

    public GymAttendance() {

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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getDoy() {
        return doy;
    }

    public void setDoy(int doy) {
        this.doy = doy;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFromhour() {
        return fromhour;
    }

    public void setFromhour(int fromhour) {
        this.fromhour = fromhour;
    }

    public int getTohour() {
        return tohour;
    }

    public void setTohour(int tohour) {
        this.tohour = tohour;
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

    public String getDatedoy() {
        return datedoy;
    }

    public void setDatedoy(String datedoy) {
        this.datedoy = datedoy;
    }
}
