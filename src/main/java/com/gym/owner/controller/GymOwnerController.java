package com.gym.owner.controller;

import com.gym.owner.dbservice.GymOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class GymOwnerController {

    @Autowired
    private  GymOwnerService gymOwnerService;
    @GetMapping("/login")
    public String login() {

        try{

            System.out.println("gymOwnerService :: "+gymOwnerService.findByUser_id(1).get().getAdress());

        }catch(Exception e){}

        return "success";
    }



}
