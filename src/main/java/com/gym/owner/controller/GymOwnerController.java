package com.gym.owner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gym.owner.DB.*;
import com.gym.owner.dbservice.*;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private GymListService gymListService;

    @CrossOrigin
    @PostMapping("/login")
    public String login(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        int gym_id =0;
        int user_id =0;
        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            System.out.println("gymOwnerService --> username "+req.get("username")+" password "+req.get("password"));
            GymOwner gymOwner = gymOwnerService.loginService(req.get("username").toString(),req.get("password").toString());
            if(gymOwner!=null){

                System.out.println("gymOwnerService :: "+gymOwner.getGym_id());
                gym_id = gymOwner.getGym_id();
                user_id = gymOwner.getUser_id();

               /* gymownerJson.put("userid", gymOwner.getUser_id());

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

*/


                statusDesc = "Login success";
                status=true;
            }else{

                System.out.println("login failed :: ");
                statusDesc = "Login failed";

            }

        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("gym_id",gym_id );
            res.put("user_id",user_id );
        }
        return res.toString();



    }
    @PostMapping("/homeData")
    public String homeData(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONObject gymownerJson = new JSONObject();
        JSONArray expenseMasterList =new JSONArray();

        int gym_id =0;
        int user_id =0;
        String  exp_total ="0";
        JSONObject res = new JSONObject();
        DecimalFormat formatter
                = new DecimalFormat("₹#,##0.00");
        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String gym_id_str = req.get("gym_id").toString();
            String user_id_str = req.get("user_id").toString();
             gym_id = Integer.valueOf(gym_id_str);
            user_id = Integer.valueOf(user_id_str);
            LocalDate todaydate = LocalDate.now();
            System.out.println("Months first date in yyyy-mm-dd: " +todaydate.withDayOfMonth(1));
            Optional<GymOwner> owner=  gymOwnerService.findByUser_id(user_id);
            Optional<GymList> gym=  gymListService.findById(gym_id);


            List<Map<String, Object>> expenseList= gymExpenseListService.getExpenseSumMonth(gym_id,new Timestamp(DATE_TIME_FORMAT.parse(todaydate.withDayOfMonth(1).toString()).getTime()));
            System.out.println("Months first date in yyyy-mm-dd: " +todaydate.withDayOfMonth(1));


            gymownerJson.put("userid", owner.get().getUser_id());

            gymownerJson.put("gymid", owner.get().getGym_id());
            gymownerJson.put("address", owner.get().getAdress());
            gymownerJson.put("name", owner.get().getName());

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
            if(!expenseList.isEmpty()){

                for(Map<String, Object> expense:expenseList){
                    JSONObject expenseItem = new JSONObject();
                 Object amount = ((expense.get("amount") == null) ? "0.00" : expense.get("amount"));
                    Float amt = Float.valueOf(amount.toString());
                    exp_total = formatter.format(amt);


                }

            }

            status = true;
            statusDesc = "Success";
        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("exp_total",exp_total);
            res.put("gymowner",gymownerJson );
            res.put("expenseMasterList",expenseMasterList );
        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/addExpense")
    public String addExpense(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray expenseJson =new JSONArray();
        DecimalFormat formatter
                = new DecimalFormat("₹#,##0.00");
        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String gym_id_str = req.get("gym_id").toString();
            String user_id_str = req.get("user_id").toString();
            String expDate = req.get("expDate").toString();
            String exp_id_str = req.get("exp_id").toString();
            String exp_remarks = req.get("exp_remarks").toString();
            String exp_amount_str = req.get("amount").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            int exp_id = Integer.valueOf(exp_id_str);
            int user_id = Integer.valueOf(user_id_str);
            float exp_amount = Float.valueOf(exp_amount_str);



            GymExpenseList gymExpenseList = new GymExpenseList();
            gymExpenseList.setGym_id(gym_id);
            gymExpenseList.setExp_remarks(exp_remarks);
            gymExpenseList.setCreated_by(user_id);
            gymExpenseList.setUpdated_by(user_id);
            gymExpenseList.setStatus(true);
            gymExpenseList.setAmount(exp_amount);
            gymExpenseList.setExp_id(exp_id);
            gymExpenseList.setCreated_on(new Timestamp(DATE_TIME_FORMAT.parse(expDate).getTime()));
            gymExpenseList.setUpdated_on(new Timestamp(DATE_TIME_FORMAT.parse(expDate).getTime()));

           GymExpenseList gymExpenseList1 = gymExpenseListService.saveExpenses(gymExpenseList);
            if(gymExpenseList1!=null){
                System.out.println("gymExpenseListService :: "+gymExpenseList1.getGym_id());
                statusDesc = "Expenses added successfully";
                status = true;
            }else{

                statusDesc = "Operation failed";
            }



            List<Map<String, Object>> expenseList= gymExpenseListService.getGymExpenseListQuery(gym_id,10,0);

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


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("expenseList",expenseJson );
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
                = new DecimalFormat("₹#,##0.00");
        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String gym_id_str = req.get("gym_id").toString();
            String offset_str = req.get("offset").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            int offset = Integer.valueOf(offset_str);
            List<Map<String, Object>> expenseList= gymExpenseListService.getGymExpenseListQuery(gym_id,10,offset);

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
