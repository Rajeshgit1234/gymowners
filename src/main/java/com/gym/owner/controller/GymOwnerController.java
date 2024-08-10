package com.gym.owner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymExpenseList;
import com.gym.owner.DB.GymExpenseListQuery;
import com.gym.owner.DB.GymOwner;
import com.gym.owner.dbservice.ExpenseMasterService;
import com.gym.owner.dbservice.GymExpenseListQueryService;
import com.gym.owner.dbservice.GymExpenseListService;
import com.gym.owner.dbservice.GymOwnerService;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
public class GymOwnerController {

    @Autowired
    private  GymOwnerService gymOwnerService;

    @Autowired
    private ExpenseMasterService expenseMasterService;

    @Autowired
    private GymExpenseListService gymExpenseListService;

    @Autowired
    private GymExpenseListQueryService gymExpenseListQueryService;

    @PostMapping("/login")
    public String login(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONObject gymownerJson = new JSONObject();
        JSONArray expenseMasterList =new JSONArray();

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
               // List<GymExpenseListQuery> expenseList= gymExpenseListService.getGymExpenseListQuery(gym_id);

                if(!expenseMaster.isEmpty()){

                   for(ExpenseMaster expense:expenseMaster){
                        JSONObject expenseJson = new JSONObject();
                       expenseJson.put("expId",expense.getId());
                       expenseJson.put("expItem",expense.getExpense_item());
                       expenseMasterList.put(expenseJson);
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
    @CrossOrigin
    @PostMapping("/loadExpenses")

    public String loadExpenses(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";

        JSONArray expenseJson =new JSONArray();
        DecimalFormat formatter
                = new DecimalFormat("$#,##0.00");
        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String gym_id_str = req.get("gym_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            List<Map<String, Object>> expenseList= gymExpenseListService.getGymExpenseListQuery(gym_id);

            if(!expenseList.isEmpty()){

                for(Map<String, Object> expense:expenseList){
                    JSONObject expenseItem = new JSONObject();
                    Object amount = ((expense.get("amount") == null) ? "0.00" : expense.get("amount"));
                    BigDecimal amt = new BigDecimal(amount.toString());
                    String currStr = formatter.format(amt);

                    expenseItem.put("exp_id",expense.get("id"));
                    expenseItem.put("created_by",((expense.get("name") == null) ? "N/A" : expense.get("name")));
                    expenseItem.put("created_on",((expense.get("created_on") == null) ? "N/A" : expense.get("created_on")));
                    expenseItem.put("expense_item",((expense.get("expense_item") == null) ? "N/A" : expense.get("expense_item")));
                    expenseItem.put("exp_remarks",((expense.get("exp_remarks") == null) ? "N/A" : expense.get("exp_remarks")));
                    expenseItem.put("exp_amount",currStr);

                    expenseJson.put(expenseItem);
                }

            }
            statusDesc = "Data fetched";
            status=true;


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("expenseList",expenseJson );
        }

        return res.toString();



    }



}
