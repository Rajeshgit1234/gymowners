package com.gym.owner.controller;

import com.gym.owner.dbservice.GymOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GymOwnerController {

    @Autowired
    private  GymOwnerService gymOwnerService;
    @GetMapping("/login")
    public String login() {

        try{

            System.out.println("gymOwnerService :: "+gymOwnerService.findAll());

        }catch(Exception e){}

        return "success";
    }



}
