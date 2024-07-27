package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface GymOwnerRepo extends JpaRepository<GymOwner, Integer> {



}
