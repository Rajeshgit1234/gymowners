package com.gym.owner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymExpenseList;
import com.gym.owner.DB.GymOwner;
import com.gym.owner.dbservice.ExpenseMasterService;
import com.gym.owner.dbservice.GymExpenseListService;
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

    @Autowired
    private GymExpenseListService gymExpenseListService;

    @PostMapping("/login")
    public String login(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONObject gymownerJson = new JSONObject();
        JSONArray expenseMasterList =new JSONArray();
        JSONArray expenseListJson =new JSONArray();
        int gym_id =0;
        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            System.out.println("gymOwnerService --> username "+req.get("username")+" password "+req.get("password"));
            GymOwner gymOwner = gymOwnerService.loginService(req.get("username").toString(),req.get("password").toString());
            if(gymOwner!=null){

                System.out.println("gymOwnerService :: "+gymOwner.getGym_id());
                gym_id = gymOwner.getGym_id();

                gymownerJson.put("userid", gymOwner.getUser_id());

                gymownerJson.put("gymid", gymOwner.getGym_id());
                gymownerJson.put("address", gymOwner.getAdress());
                gymownerJson.put("name", gymOwner.getName());

                List<ExpenseMaster> expenseMaster= expenseMasterService.findActiveExpenseMaster(gym_id);
                List<GymExpenseList> expenseList= gymExpenseListService.getGymExpenseList(gym_id);

                if(!expenseMaster.isEmpty()){

                   for(ExpenseMaster expense:expenseMaster){
                        JSONObject expenseJson = new JSONObject();
                       expenseJson.put("expId",expense.getId());
                       expenseJson.put("expItem",expense.getExpense_item());
                       expenseMasterList.put(expenseJson);
                   }

                }
                if(!expenseList.isEmpty()){

                   for(GymExpenseList expense:expenseList){
                        JSONObject expenseItem = new JSONObject();
                       expenseItem.put("id",expense.getId());
                       expenseItem.put("exp_id",expense.getExp_id());
                       expenseItem.put("created_by",expense.getCreated_by());
                       expenseItem.put("updated_by",expense.getUpdated_by());
                       expenseItem.put("gym_id",expense.getGym_id());
                       expenseItem.put("created_on",expense.getCreated_on());
                       expenseItem.put("updated_on",expense.getUpdated_on());
                       expenseItem.put("exp_remarks",expense.getExp_remarks());
                       expenseItem.put("exp_amount",expense.getAmount());
                       expenseListJson.put(expenseItem);
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
            res.put("expenseMasterList",expenseMasterList );
            res.put("expenseListJson",expenseListJson );
        }
        return res.toString();



    }

    @PostMapping("/addExpense")
    public String addExpense(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray expenseList =new JSONArray();

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            statusDesc = "Operation failed";



        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("expenseList",expenseList );
        }
        return res.toString();



    }



}
