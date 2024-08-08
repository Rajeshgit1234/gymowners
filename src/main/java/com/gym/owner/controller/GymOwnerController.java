package com.gym.owner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymOwner;
import com.gym.owner.dbservice.ExpenseMasterService;
import com.gym.owner.dbservice.GymOwnerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
public class GymOwnerController {

    @Autowired
    private  GymOwnerService gymOwnerService;

    @Autowired
    private ExpenseMasterService expenseMasterService;

    @PostMapping("/login")
    public String login(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONObject gymownerJson = new JSONObject();
        JSONArray jsonexpenseList =new JSONArray();

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            System.out.println("gymOwnerService --> username "+req.get("username")+" password "+req.get("password"));
            GymOwner gymOwner = gymOwnerService.loginService(req.get("username").toString(),req.get("password").toString());
            if(gymOwner!=null){

                System.out.println("gymOwnerService :: "+gymOwner.getGym_id());
                gymownerJson.put("userid", gymOwner.getUser_id());

                gymownerJson.put("gymid", gymOwner.getGym_id());
                gymownerJson.put("address", gymOwner.getAdress());
                gymownerJson.put("name", gymOwner.getName());

                List<ExpenseMaster> expenseList= expenseMasterService.findActiveExpenseMaster();
                if(expenseList.size()>0){

                   for(ExpenseMaster expense:expenseList){
                        JSONObject expenseJson = new JSONObject();
                       expenseJson.put("expId",expense.getId());
                       expenseJson.put("expItem",expense.getExpense_item());
                       jsonexpenseList.put(expenseJson);
                   }



                }


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
            res.put("expense",jsonexpenseList );
        }
        return res.toString();



    }



}
