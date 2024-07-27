package com.gym.owner.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigInteger;

@Entity
public class GymOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger user_id;

    private BigInteger gym_id;

    private String name;

    private String username;

    private String password;

    private String adress;

    private Boolean active;


}
