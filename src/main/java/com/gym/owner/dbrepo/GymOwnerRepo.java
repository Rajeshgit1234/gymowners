package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface GymOwnerRepo extends JpaRepository<GymOwner, Integer> {

    @Query("SELECT u FROM GymOwner u WHERE u.username = :username and u.password = :password and u.active=true")
    public GymOwner loginGymOwners(@Param("username") String username,@Param("password") String password);


}
