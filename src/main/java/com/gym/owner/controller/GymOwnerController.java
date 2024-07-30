package com.gym.owner.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gym.owner.DB.GymOwner;
import com.gym.owner.dbservice.GymOwnerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
public class GymOwnerController {

    @Autowired
    private  GymOwnerService gymOwnerService;
    @PostMapping("/login")
    public String login(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONObject gymownerJson = new JSONObject();

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            System.out.println("gymOwnerService --> username "+req.get("username")+" password "+req.get("password"));
            GymOwner gymOwner = gymOwnerService.loginService(req.get("username").toString(),req.get("password").toString());
            if(gymOwner!=null){

                System.out.println("gymOwnerService :: "+gymOwner.getGym_id());

                gymownerJson.put("gymid", gymOwner.getGym_id());
                gymownerJson.put("address", gymOwner.getAdress());
                gymownerJson.put("name", gymOwner.getName());

                statusDesc = "Login success";
                status=true;
            }else{

                System.out.println("login failed :: ");
                statusDesc = "Login failed";

            }

        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("gymowner",gymownerJson );
        }
        return res.toString();



    }



}
