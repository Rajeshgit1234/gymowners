package com.gym.owner.controller;

import com.gym.owner.DB.*;
import com.gym.owner.dbservice.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
public class GymOwnerController {

    @Autowired
    private GymUsersService gymUsersService;

    @Autowired
    private ExpenseMasterService expenseMasterService;

    @Autowired
    private GymExpenseListService gymExpenseListService;

    @Autowired
    private GymExpenseListQueryService gymExpenseListQueryService;

    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private GymListService gymListService;
    @Autowired
    private GymProfilesService gymProfilesService;

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
            GymUsers gymUsers = gymUsersService.loginService(req.get("username").toString(),req.get("password").toString());
            if(gymUsers !=null){

                System.out.println("gymOwnerService :: "+ gymUsers.getGym_id());
                gym_id = gymUsers.getGym_id();
                user_id = gymUsers.getId();

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
        JSONObject gymuser_Json = new JSONObject();
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
            Optional<GymUsers> owner=  gymUsersService.findByUser_id(user_id);
            Optional<GymList> gym=  gymListService.findById(gym_id);


            List<Map<String, Object>> expenseList= gymExpenseListService.getExpenseSumMonth(gym_id,new Timestamp(DATE_TIME_FORMAT.parse(todaydate.withDayOfMonth(1).toString()).getTime()));
            System.out.println("Months first date in yyyy-mm-dd: " +todaydate.withDayOfMonth(1));


            gymuser_Json.put("userid", owner.get().getId());

            gymuser_Json.put("gymid", owner.get().getGym_id());
            gymuser_Json.put("address", owner.get().getAddress());
            gymuser_Json.put("name", owner.get().getName());

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
                    if(expense!=null) {
                        JSONObject expenseItem = new JSONObject();
                        Object amount = ((expense.get("amount") == null) ? "0.00" : expense.get("amount"));
                        Float amt = Float.valueOf(amount.toString());
                        exp_total = formatter.format(amt);
                    }else{
                        exp_total = "0.00";
                    }


                }

            }

            status = true;
            statusDesc = "Success";
        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("exp_total",exp_total);
            res.put("gymuser",gymuser_Json );
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
    @PostMapping("/addNewGymOwner")
    public String addNewGymOwner(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int user_id = 0;
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewUser " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String gym_id_str = req.get("gym_id").toString();
            String name = req.get("name").toString();
            String username = req.get("username").toString();
            String password = req.get("password").toString();
            String address = req.get("address").toString();
            String profile_id_str = req.get("profile_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            int profile_id = Integer.valueOf(profile_id_str);


            GymUsers gymUsers = new GymUsers();
            gymUsers.setGym_id(gym_id);
            gymUsers.setName(name);
            gymUsers.setUsername(username);
            gymUsers.setPassword(password);
            gymUsers.setAddress(address);
            gymUsers.setProfile_id(profile_id);
            gymUsers.setActive(true);
            GymUsers owner = gymUsersService.findUserExist(gymUsers);
            GymUsers gymUsers1 = new GymUsers();
            if (owner == null){
                 gymUsers1 = gymUsersService.saveUser(gymUsers);
                if (gymUsers1 != null) {
                    System.out.println("gymExpenseListService :: " + gymUsers1.getId());
                    statusDesc = "User added successfully";
                    user_id = gymUsers1.getId();
                    status = true;
                } else {

                    statusDesc = "Operation failed";
                }
            }else{

                statusDesc = "User already exist";
            }



        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("user_id",user_id );
        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/addNewUserProfile")
    public String addNewUserProfile(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int profile_id = 0;
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewUserProfile " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String profile_name = req.get("profile_name").toString();



            GymProfiles profile = new GymProfiles();
            profile.setProfile_name(profile_name);
            profile.setStatus(true);
            GymProfiles gym_profile = gymProfilesService.saveNewProfile(profile);
            if (gym_profile == null){

                    System.out.println("gymExpenseListService :: " + gym_profile.getId());
                    statusDesc = "Profile added successfully";
                profile_id = gym_profile.getId();
                    status = true;

            }else{

                statusDesc = "Operation failed";
            }



        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("profile_id",profile_id );
        }
        return res.toString();



    }
@CrossOrigin
    @PostMapping("/addNewGym")
    public String addNewGym(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int gym_id = 0;
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewGym " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String gym_name = req.get("gym_name").toString();
            String adress = req.get("adress").toString();
            String description = req.get("description").toString();
            String added_id_str = req.get("user_id").toString();
            int user_id = Integer.valueOf(added_id_str);


            GymList gymList = new GymList();
            gymList.setDescription(description);
            gymList.setName(gym_name);
            gymList.setGym_name(gym_name);
            gymList.setAdress(adress);
            gymList.setCreated_by(user_id);
            gymList.setActive(true);
            GymList gym = gymListService.saveGym(gymList);
            if (gym == null){

                    System.out.println("gymExpenseListService :: " + gym.getId());
                    statusDesc = "User added successfully";
                    gym_id = gym.getId();
                    status = true;

            }else{

                statusDesc = "Operation failed";
            }



        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("gym_id",gym_id );
        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/checkUserNameAvailability")
    public String checkUserNameAvailability(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int user_id = 0;
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewUser " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String username = req.get("username").toString();

            GymUsers gymUsers = new GymUsers();

            gymUsers.setUsername(username);

            gymUsers.setActive(true);
            GymUsers owner = gymUsersService.findUserExist(gymUsers);

            if (owner == null){
                    status = true;
                    statusDesc = "Eligible";

            }else{

                statusDesc = "User already exist";
            }



        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
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

    @CrossOrigin
    @PostMapping("/loadProfile")

    public String loadProfile(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        JSONObject profile =new JSONObject();

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> loadProfile "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String user_id_str = req.get("user_id").toString();
            int user_id = Integer.valueOf(user_id_str);
            Optional<GymUsers> gymUsersList = gymUsersService.findByUser_id(user_id);
            if(!gymUsersList.isEmpty()){

                GymUsers gymUsers = gymUsersList.get();
                if(gymUsersList.get()!=null){

                    profile.put("username",gymUsers.getUsername());
                    profile.put("name",gymUsers.getName());
                    profile.put("address",gymUsers.getAddress());
                   // SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
                    //Date date2 = dateFormat.parse(gymUsers.getCreated().toString());
                    //Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(gymUsers.getCreated().toString());
                    //String date2 =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(gymUsers.getCreated().toString());


                    profile.put("created",date);
                    Optional<GymList> gymList = gymListService.findById(gymUsers.getGym_id());
                    if(gymList.isPresent()){

                        if(gymList.get()!=null){

                            profile.put("gym",gymList.get().getGym_name());
                            profile.put("gymaddress",gymList.get().getAdress());
                        }
                    }

                    Optional<GymProfiles> profiles = gymProfilesService.findById(gymUsers.getProfile_id());
                    if(profiles.isPresent()){
                        if(profiles.get()!=null){
                            profile.put("profile",profiles.get().getProfile_name());
                        }
                    }


                }

            }
            statusDesc = "Data fetched";
            status=true;


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("profile",profile );
        }

        return res.toString();



    }



}
