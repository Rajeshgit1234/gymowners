package com.gym.owner.controller;

import com.gym.owner.DB.*;
import com.gym.owner.common.RedisJava;
import com.gym.owner.dbservice.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Component
public class GymOwnerController {

    @Autowired
    private GymUsersService gymUsersService;

    @Autowired
    private ExpenseMasterService expenseMasterService;

    @Autowired
    private GymSubscriptionPlansService gymSubscriptionPlansService;

    @Autowired
    private GymExpenseListService gymExpenseListService;

    @Autowired
    private GymExpenseListQueryService gymExpenseListQueryService;

    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private GymListService gymListService;
    @Autowired
    private GymProfilesService gymProfilesService;
    @Autowired
    private GymUserPaymentsService   gymUserPaymentsService;

    @Autowired
    private GymOwnersService gymOwnersService;

    @Autowired
    private GymWebAccessService gymWebAccessService;

    @Autowired
    private GymAttendanceService gymAttendanceService;

    @Autowired
    private GymQueriesService gymQueriesService;

    @Autowired
    private GymDietPlansService gymDietPlansService;



   /* @Scheduled(fixedRate = 60000)
    public void setRedisCache() {


        try{
            LocalDate currentdate = LocalDate.now();
            int currentYear = currentdate.getYear();
            Calendar calendar = Calendar.getInstance();
            int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);


            Jedis jedis = RedisJava.getJedis();
           List<GymList> gymLists =  gymListService.findByActive();
           for (GymList gymList : gymLists) {
               JSONArray payemtnsPending = new JSONArray();
               JSONArray dietPlan = new JSONArray();
               JSONObject notificationJson = new JSONObject();


               List<Map<String, Object>> payemntsList= gymUserPaymentsService.getpendingPaymentsList(gymList.getId(),Common.GYM_CUSTOMERS,currentYear,dayOfYear);

               if(!payemntsList.isEmpty()) {

                   for (Map<String, Object> payemnts : payemntsList) {
                       JSONObject payItem = new JSONObject();


                       payItem.put("id", payemnts.get("id"));
                       payItem.put("name", payemnts.get("name"));
                       payemtnsPending.put(payItem);
                   }
               }

               notificationJson.put("payemntPending", payemtnsPending);
               notificationJson.put("payemntPendingCount", payemtnsPending.length());

               List<GymUsers> dietUsers = gymUsersService.findCustomerYetToAddDietPlan(gymList.getId());
               for(GymUsers user : dietUsers){

                   JSONObject userJson = new JSONObject();
                   userJson.put("name",user.getName());
                   userJson.put("id",user.getId());
                   userJson.put("phone",user.getPhone());
                   dietPlan.put(userJson);
               }
               notificationJson.put("dietPlan", dietPlan);
               notificationJson.put("dietPlanPendingCount", dietPlan.length());
                String redis_key = "not_"+gymList.getId();
               jedis.set(redis_key, notificationJson.toString());


           }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @CrossOrigin
    @PostMapping("/weblogin")
    public String login(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        int gym_id =0;
        int user_id =0;
        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            String phone = Common.inputStringParaNullCheck(req,"phone");
            String password = Common.inputStringParaNullCheck(req,"password");

            if(phone.length()!=0 && password.length()!=0) {

                if(phone.substring(0,2).equals("91")){

                    phone = phone.substring(2,phone.length());
                }
                GymUsers gymUsers = gymUsersService.loginService(phone, password);
                if (gymUsers != null) {

                    System.out.println("gymOwnerService :: " + gymUsers.getGym());
                    gym_id = gymUsers.getGym();
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
                    status = true;
                } else {

                    System.out.println("login failed :: ");
                    statusDesc = "Login failed";

                }
            }else{

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
    @CrossOrigin
    @PostMapping("/changepassword")
    public String changepassword(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        int gym_id =0;
        int user_id =0;
        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int userid = Common.inputIntParaNullCheck(req,"userid");
            String password = Common.inputStringParaNullCheck(req,"password");

            if(userid!=0 && password.length()!=0) {


                if(gymUsersService.updatePassword(userid, password)>0){;


                    statusDesc = "Change password  success";
                    status = true;
                }else{
                    statusDesc = "Change password  failed";
                }

            }else{

                statusDesc = "Change password failed";

            }


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);

        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/addNewExpItem")
    public String addNewExpItem(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";



        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int user_id = Common.inputIntParaNullCheck(req,"user_id");
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            String expItem = Common.inputStringParaNullCheck(req,"expItem");

            if(user_id!=0  && expItem.length()!=0) {


                if(expenseMasterService.checkIfExist(gym_id,expItem.toUpperCase())==null){;


                    ExpenseMaster expenseMaster = new ExpenseMaster();
                    expenseMaster.setGym_id(gym_id);
                    expenseMaster.setExpense_item(expItem);
                    expenseMaster.setStatus(true);
                    expenseMaster.setAdded(user_id);
                    expenseMaster.setUpdatedby(user_id);
                    expenseMasterService.saveExpenseMaster(expenseMaster);
                    statusDesc = "Item added success";
                    status = true;
                }else{
                    statusDesc = "Item already exist";
                }

            }else{

                statusDesc = "Mandatory fields are missing";

            }


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);

        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/markAttendance")
    public String markAttendance(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int attendanceid = 0;


        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int user_id = Common.inputIntParaNullCheck(req,"user_id");
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int year = Common.inputIntParaNullCheck(req,"year");
            int fromhour = Common.inputIntParaNullCheck(req,"fromhour");
            int tohour = Common.inputIntParaNullCheck(req,"tohour");
            int added = Common.inputIntParaNullCheck(req,"added");
            String date = Common.inputStringParaNullCheck(req,"date");

            if(user_id!=0  && gym_id!=0 && year!=0 && fromhour!=0 && tohour!=0 && added!=0 && date.length()!=0) {

                if(fromhour<tohour){
                    LocalDate dateString = LocalDate.parse(date);
                    int doy = dateString.getDayOfYear();


                    if(doy!=0) {
                        GymAttendance gymAttendance = new GymAttendance();
                        gymAttendance.setGymid(gym_id);
                        gymAttendance.setYear(year);
                        gymAttendance.setFromhour(fromhour);
                        gymAttendance.setTohour(tohour);
                        gymAttendance.setAdded(added);
                        gymAttendance.setStatus(true);
                        gymAttendance.setDoy(doy);
                        gymAttendance.setUserid(user_id);
                        gymAttendance.setDatedoy(date);
                        GymAttendance gymAttendanceNew = gymAttendanceService.save(gymAttendance);
                        if(gymAttendanceNew!=null) {

                            int recentactivity =gymUsersService.updateRecentActivity(user_id);
                            System.out.println("user_id : "+user_id+" : recentactivity is :"+recentactivity);
                            attendanceid = gymAttendanceNew.getId();
                            statusDesc = "Attendance added successfully";
                            status = true;
                        }else{

                            statusDesc = "Operation failed";
                        }

                    }else{

                        statusDesc = "Invalid date";
                    }
                }else{

                    statusDesc = "Invalid date";
                }

            }else{

                statusDesc = "Mandatory fields are missing";

            }


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("attendanceid",attendanceid);

        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/viewAttendance")
    public String viewAttendance(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        JSONObject res = new JSONObject();
        JSONArray AttJson = new JSONArray();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int offset = Common.inputIntParaNullCheck(req,"offset");

            String date = Common.inputStringParaNullCheck(req,"date");

            if( gym_id!=0  && date.length()!=0) {

                LocalDate dateString = LocalDate.parse(date);
                int doy = dateString.getDayOfYear();


            if(doy!=0) {


                List<Map<String, Object>> attendanceList= gymAttendanceService.findAllByGymId(gym_id,doy,offset);

                if(!attendanceList.isEmpty()) {

                    for (Map<String, Object> attendance : attendanceList) {
                        JSONObject AttItem = new JSONObject();


                        AttItem.put("attid", attendance.get("attid"));
                        AttItem.put("date", date);
                        AttItem.put("user", ((attendance.get("user") == null) ? "N/A" : attendance.get("user")));
                        AttItem.put("doy", ((attendance.get("doy") == null) ? "N/A" : attendance.get("doy")));
                        AttItem.put("year", ((attendance.get("year") == null) ? "N/A" : attendance.get("year")));
                        AttItem.put("fromhour", ((attendance.get("fromhour") == null) ? "N/A" : attendance.get("fromhour")));
                        AttItem.put("tohour", ((attendance.get("tohour") == null) ? "N/A" : attendance.get("tohour")));
                        AttItem.put("createdon", ((attendance.get("createdon") == null) ? "N/A" : attendance.get("createdon")));

                        AttJson.put(AttItem);
                    }
                    statusDesc = "Fetched successfully";
                    status = true;
                }else {
                    statusDesc = "Fetched successfully";
                }


            }else{

                statusDesc = "Invalid date";
            }

            }else{

                statusDesc = "Mandatory fields are missing";

            }


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("attendance",AttJson);

        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/viewAttendanceMonth")
    public String viewAttendanceMonth(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        String fromdate = "";
        String toDate = "";

        LinkedHashMap<String ,String> attHash = new LinkedHashMap<>();
        JSONObject res = new JSONObject();
        JSONObject AttJson = new JSONObject();
        JSONArray doyJson = new JSONArray();
        JSONArray attJSONArray = new JSONArray();
        JSONArray custJson = new JSONArray();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int offset = Common.inputIntParaNullCheck(req,"offset");

            String from = Common.inputStringParaNullCheck(req,"from");
            String to = Common.inputStringParaNullCheck(req,"to");

            int doyFrom = 0;
            int doyTo = 0;
            if( gym_id!=0  ) {


                if (from.length()!=0 && to.length()!=0) {

                    LocalDate dateString = LocalDate.parse(from);
                    LocalDate dateTo = LocalDate.parse(to);
                    doyFrom = dateString.getDayOfYear();
                    doyTo = dateTo.getDayOfYear();
                    toDate = to;
                    fromdate = from;
                   // toDate = dateTo.getYear()+"-"+dateTo.getMonthValue()+"-"+dateTo.getDayOfMonth();
                    //fromdate = dateString.getYear()+"-"+dateString.getMonthValue()+"-"+dateString.getDayOfMonth();
                }else{

                    Calendar c = Calendar.getInstance();
                    Calendar cend = Calendar.getInstance();
                    doyTo = c.get(Calendar.DAY_OF_YEAR);
                    cend.roll(Calendar.DAY_OF_YEAR, -15);
//if within the first 30 days, need to roll the year as well
                    if(cend.after(c)){
                        cend.roll(Calendar.YEAR, -1);
                    }
                    doyFrom = cend.get(Calendar.DAY_OF_YEAR);

                    int fMonth = cend.get(Calendar.MONTH)+1;
                    int tMonth = c.get(Calendar.MONTH)+1;
                    int fDay = cend.get(Calendar.DAY_OF_MONTH);
                    int tDay = c.get(Calendar.DAY_OF_MONTH);

                    String fromM = "";
                    String toM = "";
                    String fromDate = "";
                    String toDate2 = "";
                    if(fMonth<10) {fromM="0"+fMonth; }else {fromM=String.valueOf(fMonth);};
                    if(tMonth<10) {toM="0"+tMonth; }else {toM=String.valueOf(tMonth);};
                    if(fDay<10) {fromDate="0"+fDay; }else {fromDate=String.valueOf(fDay);};
                    if(tDay<10) {toDate2="0"+tDay; }else {toDate2=String.valueOf(tDay);};

                    fromdate = cend.get(Calendar.YEAR)+"-"+fromM+"-"+fromDate;
                    toDate = c.get(Calendar.YEAR)+"-"+toM+"-"+toDate2;

                }





            if(doyFrom!=0 && doyTo!=0) {


                /*for(int i=doyFrom;i<=doyTo;i++) {

                    int dayOfYear = i ;
                    Year y = Year.of( Year.now().getValue());
                    LocalDate ld = y.atDay( dayOfYear ) ;
                    DateTimeFormatter dateformatter
                            = DateTimeFormatter.ofPattern("dd/MM/yy");
                    // display the date
                    String date_obj = (dateformatter.format(ld));
                    JSONObject doyJsonObj = new JSONObject();
                    doyJsonObj.put("doy",i);
                    doyJsonObj.put("date",date_obj);
                    doyJson.put(doyJsonObj);

                }*/

                for(int i=doyTo;i>=doyFrom;i--) {

                    int dayOfYear = i ;
                    Year y = Year.of( Year.now().getValue());
                    LocalDate ld = y.atDay( dayOfYear ) ;
                    DateTimeFormatter dateformatter
                            = DateTimeFormatter.ofPattern("dd/MM/yy");
                    // display the date
                    String date_obj = (dateformatter.format(ld));
                    JSONObject doyJsonObj = new JSONObject();
                    doyJsonObj.put("doy",i);
                    doyJsonObj.put("date",date_obj);
                    doyJson.put(doyJsonObj);

                }



                    List<Map<String, Object>> attendanceList= gymAttendanceService.fetchGymAttendance(gym_id,Year.now().getValue(),doyFrom,doyTo,offset);

                    if(!attendanceList.isEmpty()) {

                        for (Map<String, Object> attendance : attendanceList) {
                            JSONObject AttItem = new JSONObject();


                            String userId = (attendance.get("userid") == null) ? "" : attendance.get("userid").toString();
                            String userName = (attendance.get("name") == null) ? "" : attendance.get("name").toString();

                            AttItem.put("userid", (userId));
                            AttItem.put("name", (userName));
                            AttItem.put("doy", ((attendance.get("doy") == null) ? "" : attendance.get("doy")));
                            AttItem.put("datedoy", ((attendance.get("datedoy") == null) ? "" : attendance.get("datedoy")));
                            attJSONArray.put(AttItem);

                            if(attHash.get(userId)==null) {

                                attHash.put(userId,userName);
                            }
                           /* AttItem.put("attid", attendance.get("attid"));
                            AttItem.put("date", date);
                            AttItem.put("user", ((attendance.get("user") == null) ? "N/A" : attendance.get("user")));
                            AttItem.put("doy", ((attendance.get("doy") == null) ? "N/A" : attendance.get("doy")));
                            AttItem.put("year", ((attendance.get("year") == null) ? "N/A" : attendance.get("year")));
                            AttItem.put("fromhour", ((attendance.get("fromhour") == null) ? "N/A" : attendance.get("fromhour")));
                            AttItem.put("tohour", ((attendance.get("tohour") == null) ? "N/A" : attendance.get("tohour")));
                            AttItem.put("createdon", ((attendance.get("createdon") == null) ? "N/A" : attendance.get("createdon")));*/

                            //AttJson.put(AttItem);
                        }



                        if (!attHash.isEmpty()){


                            for (Map.Entry<String, String> set :
                                    attHash.entrySet()) {

                                String cust_id =set.getKey();
                                String cust_name =set.getValue();
                                JSONObject custJsonObj = new JSONObject();
                                custJsonObj.put("cust_id",cust_id);
                                custJsonObj.put("cust_name",cust_name);
                                custJson.put( custJsonObj );


                            }

                        }
                        statusDesc = "Fetched successfully";
                        status = true;
                    }



            }else{

                statusDesc = "Invalid date";
            }

            }else{

                statusDesc = "Mandatory fields are missing";

            }


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("attendance",attJSONArray);
            res.put("doyJson",doyJson);
            res.put("custJson",custJson);
            res.put("fromdate",fromdate);
            res.put("toDate",toDate);

        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/viewCustomerAttendanceMonth")
    public String viewCustomerAttendanceMonth(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        String fromdate = "";
        String toDate = "";

        LinkedHashMap<String ,String> attHash = new LinkedHashMap<>();
        JSONObject res = new JSONObject();
        JSONObject AttJson = new JSONObject();
        JSONArray doyJson = new JSONArray();
        JSONArray attJSONArray = new JSONArray();
        JSONArray custJson = new JSONArray();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int customer = Common.inputIntParaNullCheck(req,"customer");


            int doyFrom = 0;
            int doyTo = 0;
            if( gym_id!=0  && customer!=0 ) {




            Calendar c = Calendar.getInstance();
            Calendar cend = Calendar.getInstance();
            doyTo = c.get(Calendar.DAY_OF_YEAR);
            cend.roll(Calendar.DAY_OF_YEAR, -15);
            if(cend.after(c)){
                cend.roll(Calendar.YEAR, -1);
            }
            doyFrom = cend.get(Calendar.DAY_OF_YEAR);

            int fMonth = cend.get(Calendar.MONTH)+1;
            int tMonth = c.get(Calendar.MONTH)+1;
            int fDay = cend.get(Calendar.DAY_OF_MONTH);
            int tDay = c.get(Calendar.DAY_OF_MONTH);

            String fromM = "";
            String toM = "";
            String fromDate = "";
            String toDate2 = "";
            if(fMonth<10) {fromM="0"+fMonth; }else {fromM=String.valueOf(fMonth);};
            if(tMonth<10) {toM="0"+tMonth; }else {toM=String.valueOf(tMonth);};
            if(fDay<10) {fromDate="0"+fDay; }else {fromDate=String.valueOf(fDay);};
            if(tDay<10) {toDate2="0"+tDay; }else {toDate2=String.valueOf(tDay);};

            fromdate = cend.get(Calendar.YEAR)+"-"+fromM+"-"+fromDate;
            toDate = c.get(Calendar.YEAR)+"-"+toM+"-"+toDate2;


            if(doyFrom!=0 && doyTo!=0) {




                for(int i=doyTo;i>=doyFrom;i--) {

                    int dayOfYear = i ;
                    Year y = Year.of( Year.now().getValue());
                    LocalDate ld = y.atDay( dayOfYear ) ;
                    DateTimeFormatter dateformatter
                            = DateTimeFormatter.ofPattern("dd/MM/yy");
                    // display the date
                    String date_obj = (dateformatter.format(ld));
                    JSONObject doyJsonObj = new JSONObject();
                    doyJsonObj.put("doy",i);
                    doyJsonObj.put("date",date_obj);
                    doyJson.put(doyJsonObj);

                }



                    List<Map<String, Object>> attendanceList= gymAttendanceService.fetchGymCustomerAttendance(gym_id,customer,Year.now().getValue(),doyFrom,doyTo);

                    if(!attendanceList.isEmpty()) {

                        for (Map<String, Object> attendance : attendanceList) {
                            JSONObject AttItem = new JSONObject();


                            String userId = (attendance.get("userid") == null) ? "" : attendance.get("userid").toString();
                            String userName = (attendance.get("name") == null) ? "" : attendance.get("name").toString();

                            AttItem.put("userid", (userId));
                            AttItem.put("name", (userName));
                            AttItem.put("doy", ((attendance.get("doy") == null) ? "" : attendance.get("doy")));
                            AttItem.put("datedoy", ((attendance.get("datedoy") == null) ? "" : attendance.get("datedoy")));
                            attJSONArray.put(AttItem);

                            if(attHash.get(userId)==null) {

                                attHash.put(userId,userName);
                            }

                        }



                        if (!attHash.isEmpty()){


                            for (Map.Entry<String, String> set :
                                    attHash.entrySet()) {

                                String cust_id =set.getKey();
                                String cust_name =set.getValue();
                                JSONObject custJsonObj = new JSONObject();
                                custJsonObj.put("cust_id",cust_id);
                                custJsonObj.put("cust_name",cust_name);
                                custJson.put( custJsonObj );


                            }

                        }
                        statusDesc = "Fetched successfully";
                        status = true;
                    }



            }else{

                statusDesc = "Invalid date";
            }

            }else{

                statusDesc = "Mandatory fields are missing";

            }


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("attendance",attJSONArray);
            res.put("doyJson",doyJson);
            res.put("custJson",custJson);
            res.put("fromdate",fromdate);
            res.put("toDate",toDate);

        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/getExpMasterList")
    public String getExpMasterList(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray expMasterList = new JSONArray();


        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);


            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int offset = Common.inputIntParaNullCheck(req,"offset");


            if(gym_id!=0  ) {


                List<Map<String, Object>> listExpMasters = expenseMasterService.findActiveExpenseMasterBasesGYM(gym_id,offset);
                for (Map<String, Object> map : listExpMasters) {

                    JSONObject expMaster = new JSONObject();
                    expMaster.put("expItem", map.get("expItem"));
                    expMaster.put("addedby", map.get("addedby"));
                    expMaster.put("expId", map.get("expId"));
                    String added = "";
                    if (map.get("added") !=null){added=map.get("added").toString();}
                    expMaster.put("added",added );
                    expMasterList.put(expMaster);

                }
                status =true;
                statusDesc="Data fetched successfully";

            }else{

                statusDesc = "Mandatory fields are missing";

            }


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("expMasterList",expMasterList);

        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/delExpMasterItem")
    public String delExpMasterItem(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray expMasterList = new JSONArray();


        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);


            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int userid = Common.inputIntParaNullCheck(req,"userid");
            int itemId = Common.inputIntParaNullCheck(req,"itemId");


            if(gym_id!=0  && itemId!=0) {

                if(expenseMasterService.checkIfExistItemId(gym_id,itemId)!=null){


                    if( expenseMasterService.updateExpMasterItem(gym_id,userid,itemId)!=0){

                        status = true;
                        statusDesc="Data deleted successfully";
                    }else{

                        statusDesc="Operation failed";

                    }
                }else{

                    statusDesc="Item not exist";
                }





            }else{

                statusDesc = "Mandatory fields are missing";

            }


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("expMasterList",expMasterList);

        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/markgymowner")
    public String markgymowner(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        int id =0;
        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> jsonReq "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int gym = Common.inputIntParaNullCheck(req,"gym");
            int ownerid = Common.inputIntParaNullCheck(req,"owner");
            int addedby = Common.inputIntParaNullCheck(req,"addedby");

            if(gym!=0 && ownerid!=0 && addedby!=0) {

                GymOwners owner = new GymOwners();
                owner.setActive(true);
                owner.setOwner(ownerid);
                owner.setGymid(gym);
                owner.setAddedby(addedby);


                GymOwners ownerisExists = gymOwnersService.findOwnerExist(owner);
                if (ownerisExists == null) {

                    GymOwners newOwner = new GymOwners();
                    newOwner = gymOwnersService.saveOwner(owner);
                    System.out.println("markgymowner :: " + newOwner.getId());
                    id = newOwner.getId();

                    statusDesc = "Marked as Gym Owner ";
                    status = true;
                } else {


                    statusDesc = "User is already owner for the same gym";

                }
            }else{

                statusDesc = "wrong user";
            }

        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("id",id );
        }
        return res.toString();



    }
    @PostMapping("/homeData")
    public String homeData(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONObject gymuser_Json = new JSONObject();
        JSONArray expenseMasterList =new JSONArray();
        JSONArray profileMasterList =new JSONArray();
        JSONArray subscriptionplans =new JSONArray();
        JSONArray paymentsList =new JSONArray();
        JSONArray expData =new JSONArray();
        JSONArray gymList =new JSONArray();
        Hashtable<String,Float > expTable = new Hashtable<String,Float>();
        Hashtable<String,Float > payTable = new Hashtable<String,Float>();

        int gym_id =0;
        int user_id =0;
        String  exp_total ="0";
        String  pay_total ="0";
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

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH)+1;

            String queryDate =year+"-"+month+"-01";
            Date fromDate = DATE_TIME_FORMAT.parse(queryDate);
            java.sql.Date qDate = new java.sql.Date(fromDate.getTime());


            List<Map<String, Object>> expenseList= gymExpenseListService.getExpenseSumMonth(gym_id,new Timestamp(DATE_TIME_FORMAT.parse(todaydate.withDayOfMonth(1).toString()).getTime()));
            List<Map<String, Object>> payList= gymUserPaymentsService.getPaySumMonth(gym_id,qDate);
            List<Map<String, Object>> payFullList= gymUserPaymentsService.getGymPaymentsFilterMonth(gym_id,qDate);
            System.out.println("Months first date in yyyy-mm-dd: " +todaydate.withDayOfMonth(1));
            List<GymProfiles> gymProfiles =  gymProfilesService.findAllProfiles();

            gymuser_Json.put("userid", owner.get().getId());

            gymuser_Json.put("gymid", owner.get().getGym());
            gymuser_Json.put("address", owner.get().getAddress());
            gymuser_Json.put("name", owner.get().getName());
            gymuser_Json.put("phone", owner.get().getPhone());
            gymuser_Json.put("profile", owner.get().getProfile());
            gymuser_Json.put("email", owner.get().getEmail());

            List<ExpenseMaster> expenseMaster= expenseMasterService.findActiveExpenseMaster(gym_id);
            List<GymSubscriptionPlans> subscription= gymSubscriptionPlansService.getPlansFull(gym_id);
            // List<GymExpenseListQuery> expenseList= gymExpenseListService.getGymExpenseListQuery(gym_id);

            if(!expenseMaster.isEmpty()){

                for(ExpenseMaster expense:expenseMaster){
                    JSONObject expenseJson = new JSONObject();
                    expenseJson.put("expId",expense.getId());
                    expenseJson.put("expItem",expense.getExpense_item());
                    expenseMasterList.put(expenseJson);
                }

            }

            if(!subscription.isEmpty()){

                for(GymSubscriptionPlans item:subscription){
                    JSONObject sub = new JSONObject();
                    sub.put("subId",item.getId());
                    sub.put("subName",item.getDescription());
                    sub.put("subAmount",item.getAmount());
                    subscriptionplans.put(sub);
                }

            }

            if(!gymProfiles.isEmpty()){

                for(GymProfiles profile:gymProfiles){
                    JSONObject profileJson = new JSONObject();
                    profileJson.put("id",profile.getId());
                    profileJson.put("name",profile.getProfile_name());
                    profileMasterList.put(profileJson);
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
            if(!payList.isEmpty()){

                for(Map<String, Object> pay:payList){
                    if(pay!=null) {
                        JSONObject expenseItem = new JSONObject();
                        Object amount = ((pay.get("amount") == null) ? "0.00" : pay.get("amount"));
                        Float amt = Float.valueOf(amount.toString());
                       pay_total = formatter.format(amt);
                    }else{
                        pay_total = "0.00";
                    }


                }

            }

            if(!payFullList.isEmpty()){

                for(Map<String, Object> pay:payFullList){
                    if(pay!=null) {
                        JSONObject payItem = new JSONObject();
                        Object amount = ((pay.get("amount") == null) ? "0.00" : pay.get("amount"));
                        String date_str = ((pay.get("createdon") == null) ? "0.00" : pay.get("createdon")).toString();
                        Float amt = Float.valueOf(amount.toString());

                        if(payTable.get(date_str)==null){

                            payTable.put(date_str,amt);

                        }else{

                            Float amount_added = payTable.get(date_str);
                            amount_added= amount_added+amt;
                            payTable.put(date_str,amount_added);

                        }


                    }

                }

                Set<String> keys = payTable.keySet();
                for(String key: keys){
                    Float amount = payTable.get(key);
                    JSONObject payItem = new JSONObject();
                    payItem.put("pay_date",key);
                    payItem.put("pay_amount",amount);
                    paymentsList.put(payItem);

                }

            }

            if(owner.get().getProfile()==Common.GYM_OWNERS){

                List<GymOwners>  ownerGymList = gymOwnersService.getAllOwnerGymDetails(owner.get().getId());

                if(!ownerGymList.isEmpty()){

                    for (GymOwners own:ownerGymList) {


                        JSONObject ownerList = new JSONObject();
                        ownerList.put("gymid",own.getGymid());
                        gymList.put(ownerList);
                    }



                }

            }


            List<Map<String, Object>> expenseListPie= gymExpenseListService.getExpenseChart(gym_id,new Timestamp(DATE_TIME_FORMAT.parse(todaydate.withDayOfMonth(1).toString()).getTime()));


            if(!expenseListPie.isEmpty()) {

                for (Map<String, Object> expense : expenseListPie) {
                    JSONObject expenseItem = new JSONObject();
                    String expitem = ((expense.get("expitem") == null) ? "" : expense.get("expitem")).toString();
                    String amt = ((expense.get("amt") == null) ? "0" : expense.get("amt")).toString();
                    //String currStr = formatter.format(amt);
                    Float amount = Float.valueOf(amt);
                    if (expTable.get(expitem) == null) {

                        expTable.put(expitem, amount);
                    } else {


                        Float amount_sum = expTable.get(expitem);
                        amount_sum = amount_sum + amount;
                        expTable.put(expitem, amount_sum);
                    }


                }


                Set<String> keys = expTable.keySet();
                for (String key : keys) {
                    Float amount = expTable.get(key);
                    JSONObject expenseItem = new JSONObject();
                    expenseItem.put("expItem", key);
                    expenseItem.put("amount", amount);
                    expData.put(expenseItem);

                }
            }



            status = true;
            statusDesc = "Success";
        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("exp_total",exp_total);
            res.put("pay_total",pay_total);
            res.put("gymuser",gymuser_Json );
            res.put("expenseMasterList",expenseMasterList );
            res.put("gymList",gymList );
            res.put("profileMasterList",profileMasterList );
            res.put("subscriptionplans",subscriptionplans );
            res.put("payList",paymentsList );
            res.put("expData",expData );

            System.out.println(res );
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
    @PostMapping("/addSubscriptions")
    public String addSubscriptions(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray expenseJson =new JSONArray();
        DecimalFormat formatter
                = new DecimalFormat("₹#,##0.00");
        JSONObject res = new JSONObject();
        int subscriptionid = 0;

        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String subscriptiontext = Common.inputStringParaNullCheck(req,"subscriptiontext");
            int gym = Common.inputIntParaNullCheck(req,"gym");
            int user = Common.inputIntParaNullCheck(req,"user");
            int amount = Common.inputIntParaNullCheck(req,"amount");
            int duration = Common.inputIntParaNullCheck(req,"duration");


            GymSubscriptionPlans gymSubscriptionPlans   = new  GymSubscriptionPlans();

            gymSubscriptionPlans.setGym(gym);
            gymSubscriptionPlans.setAmount(amount);
            gymSubscriptionPlans.setAdded(user);
            gymSubscriptionPlans.setUpdated(user);
            gymSubscriptionPlans.setStatus(true);
            gymSubscriptionPlans.setDescription(subscriptiontext);
            gymSubscriptionPlans.setDuration(duration);

            if(gymSubscriptionPlansService.checkIfExist(gymSubscriptionPlans)==null){

                GymSubscriptionPlans gymSubscriptionPlans1 = gymSubscriptionPlansService.save(gymSubscriptionPlans);
                if(gymSubscriptionPlans1!=null){
                    System.out.println("gymExpenseListService :: "+gymSubscriptionPlans1.getId());
                    subscriptionid = gymSubscriptionPlans1.getId();
                    statusDesc = "Subscription added successfully";
                    status = true;
                }else{

                    statusDesc = "Operation failed";
                }
            }else{
                statusDesc = "Subscription already exists";
            }




        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("subscriptionid",subscriptionid );
        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/fetchSubscriptionPlans")
    public String fetchSubscriptionPlans(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray subscriptionPlans =new JSONArray();

        JSONObject res = new JSONObject();
        int subscriptionid = 0;

        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            int gym = Common.inputIntParaNullCheck(req,"gym");
            int offset = Common.inputIntParaNullCheck(req,"offset");


            if(gym!=0){

                List<Map<String, Object>> subscriptionPlanList= gymSubscriptionPlansService.getPlans(gym,offset);
                if (!subscriptionPlanList.isEmpty()) {

                    /*for (GymSubscriptionPlans plans : subscriptionPlanList) {

                        JSONObject plan = new JSONObject();
                        plan.put("subId", plans.getId());
                        plan.put("subName", plans.getDescription());
                        plan.put("addedOn", plans.getCreatedon());
                        plan.put("addedBy", plans.getCreatedon());
                        subscriptionPlans.put(plan);
                    }*/

                    for(Map<String, Object> plans:subscriptionPlanList) {
                        String  subId = ((plans.get("subId") == null) ? "" : plans.get("subId").toString());
                        String  subName = ((plans.get("subName") == null) ? "" : plans.get("subName").toString());
                        String  addedOn = ((plans.get("addedOn") == null) ? "" : plans.get("addedOn").toString());
                        String  addedBy = ((plans.get("addedBy") == null) ? "" : plans.get("addedBy").toString());
                        JSONObject plan = new JSONObject();
                        plan.put("subId", subId);
                        plan.put("subName", subName);
                        plan.put("addedOn", addedOn);
                        plan.put("addedBy", addedBy);
                        subscriptionPlans.put(plan);

                    }
                    statusDesc="Fetched Successfully";
                    status = true;
                }
            }else{
                statusDesc="Mandatory fields missing";
            }




        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("subscriptionPlans",subscriptionPlans );
        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/fetchSubscriptionPlansFull")
    public String fetchSubscriptionPlansFull(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray subscriptionPlans =new JSONArray();

        JSONObject res = new JSONObject();
        int subscriptionid = 0;

        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            int gym = Common.inputIntParaNullCheck(req,"gym");


            if(gym!=0){

                List<GymSubscriptionPlans> subscriptionPlanList= gymSubscriptionPlansService.getPlansFull(gym);
                if (!subscriptionPlanList.isEmpty()) {

                    /*for (GymSubscriptionPlans plans : subscriptionPlanList) {

                        JSONObject plan = new JSONObject();
                        plan.put("subId", plans.getId());
                        plan.put("subName", plans.getDescription());
                        plan.put("addedOn", plans.getCreatedon());
                        plan.put("addedBy", plans.getCreatedon());
                        subscriptionPlans.put(plan);
                    }*/

                    for(GymSubscriptionPlans plans:subscriptionPlanList) {

                        JSONObject plan = new JSONObject();

                        plan.put("subId",plans.getId());
                        plan.put("subName",plans.getDescription());
                        plan.put("subAmount",plans.getAmount());
                        plan.put("duration",plans.getDuration());
                        subscriptionPlans.put(plan);


                    }
                    statusDesc="Fetched Successfully";
                    status = true;
                }
            }else{
                statusDesc="Mandatory fields missing";
            }




        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("subscriptionPlans",subscriptionPlans );
        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/EditExpense")
    public String EditExpense(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray expenseJson =new JSONArray();
        DecimalFormat formatter
                = new DecimalFormat("₹#,##0.00");
        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> EditExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            /*String gym_id_str = req.get("gym_id").toString();
            String user_id_str = req.get("user_id").toString();
            String expDate = req.get("expDate").toString();
            String exp_id_str = req.get("exp_id").toString();
            String exp_remarks = req.get("exp_remarks").toString();
            String exp_amount_str = req.get("amount").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            int exp_id = Integer.valueOf(exp_id_str);
            int user_id = Integer.valueOf(user_id_str);
            float exp_amount = Float.valueOf(exp_amount_str);*/

            int id = Common.inputIntParaNullCheck(req,"id");
            int exp_id = Common.inputIntParaNullCheck(req,"exp_id");
            String expDate = Common.inputStringParaNullCheck(req,"expDate");
            String exp_remarks = Common.inputStringParaNullCheck(req,"exp_remarks");
            float amount = Common.inputFloatParaNullCheck(req,"amount");


           int count = gymExpenseListService.editExpenses(id,exp_id,new Timestamp(DATE_TIME_FORMAT.parse(expDate).getTime()),exp_remarks,amount);



             if(count!=0){
                statusDesc = "Expenses edited successfully";
                status = true;
            }else{

                statusDesc = "Operation failed";
            }




        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("expenseList",expenseJson );
        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/delExpense")
    public String delExpense(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int count =0;

        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);



            int exp_id = Common.inputIntParaNullCheck(req,"exp_id");
            int updatedby = Common.inputIntParaNullCheck(req,"updatedby");


            if(updatedby!=0) {
                count = gymExpenseListService.deleteExpenses(exp_id, updatedby);


                if (count != 0) {
                    statusDesc = "Expenses deleted successfully";
                    status = true;
                } else {

                    statusDesc = "Operation failed";
                }
            }else{

                statusDesc = "Wrong user";
            }








        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("count",count );
        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/delSubscriptionPlan")
    public String delSubscriptionPlan(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int count =0;

        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);



            int gym = Common.inputIntParaNullCheck(req,"gym");
            int subId = Common.inputIntParaNullCheck(req,"subId");


            if(subId!=0) {
                List<GymSubscriptionPlans> subscription = gymSubscriptionPlansService.getPlansFullById(gym,subId);
                if(subscription!=null && subscription.size()>0){


                    int delFalg = gymSubscriptionPlansService.delSubscription(subId);
                    if(delFalg!=0){

                        statusDesc = "Subscription deleted successfully";
                        status = true;
                    }else{
                        statusDesc = "Operation failed";
                    }
                }else{
                    statusDesc = "Wrong Subscription plan";
                }



            }else{

                statusDesc = "Wrong id";
            }








        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("count",count );
        }
        return res.toString();



    }



    @CrossOrigin
    @PostMapping("/delPay")
    public String delPay(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int count =0;

        JSONObject res = new JSONObject();

        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);



            int pay_id = Common.inputIntParaNullCheck(req,"pay_id");
            int userid = Common.inputIntParaNullCheck(req,"userid");


        if(userid!=0) {
            count = gymUserPaymentsService.deletePayment(pay_id, userid);


            if (count != 0) {
                statusDesc = "Payment data deleted successfully";
                status = true;
            } else {

                statusDesc = "Operation failed";
            }
        }else{
            statusDesc = "Wrong user";
        }







        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("count",count );
        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/addNewGymUser")
    public String addNewGymUser(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int user_id = 0;
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewUser " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);
           // String gym_id_str = req.get("gym_id").toString();
            //String password = req.get("password").toString();
            //String address = req.get("address").toString();
            //String ph = req.get("phone").toString();
           // String email = req.get("email").toString();

            int user = Common.inputIntParaNullCheck(req,"user");
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int profile_id = Common.inputIntParaNullCheck(req,"profile_id");
            String name = Common.inputStringParaNullCheck(req,"name");
            String username = Common.inputStringParaNullCheck(req,"username");
            String password = Common.inputStringParaNullCheck(req,"password");
            String address = Common.inputStringParaNullCheck(req,"address");
            String phone = Common.inputStringParaNullCheck(req,"phone");
            String email = Common.inputStringParaNullCheck(req,"email");
            int subscription = Common.inputIntParaNullCheck(req,"subscription");
            int dietPlan = Common.inputIntParaNullCheck(req,"dietPlan");
            boolean diet = false;
            if(dietPlan==1){

                 diet = true;
            }

            long uniqueIDL = System.currentTimeMillis();
            String uniqueID = uniqueIDL+""+UUID.randomUUID().toString();


            if(phone.substring(0,2).equals("91")){

                phone = phone.substring(2,phone.length());
            }
            GymUsers gymUsers = new GymUsers();
            gymUsers.setGym(gym_id);
            gymUsers.setName(name);
            gymUsers.setUsername(username);
            gymUsers.setPassword(password);
            gymUsers.setAddress(address);
            gymUsers.setProfile(profile_id);
            gymUsers.setActive(true);
            gymUsers.setPhone(phone);
            gymUsers.setEmail(email);
            gymUsers.setAddedby(user);
            gymUsers.setUniquetoken(uniqueID);
            gymUsers.setUpdatedby(0);
            gymUsers.setApplog(false);
            gymUsers.setWeblog(false);
            gymUsers.setPt(0);
            gymUsers.setSubscription(subscription);
            gymUsers.setRecentactivity(0);
            gymUsers.setDiet(diet);
            gymUsers.setDietplan(dietPlan);
            GymUsers owner = gymUsersService.findUserExist(gymUsers);
            GymUsers gymUsers1 = new GymUsers();
            if (owner == null){
                 gymUsers1 = gymUsersService.saveUser(gymUsers);
                if (gymUsers1 != null) {
                    System.out.println("gymExpenseListService :: " + gymUsers1.getId());
                    statusDesc = "User added successfully";
                    user_id = gymUsers1.getId();
                    Common.sendRegisterLink(uniqueID,phone);
                    status = true;
                    if(profile_id==Common.GYM_OWNERS){

                        GymOwners owner1 = new GymOwners();
                        owner1.setActive(true);
                        owner1.setOwner(user_id);
                        owner1.setGymid(gym_id);
                        owner1.setAddedby(user);


                        GymOwners ownerisExists = gymOwnersService.findOwnerExist(owner1);
                        if (ownerisExists == null) {

                            GymOwners newOwner = new GymOwners();
                            newOwner = gymOwnersService.saveOwner(owner1);
                            System.out.println("markgymowner :: " + newOwner.getId());

                        }

                    }
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
    @PostMapping("/editGymUser")
    public String editGymUser(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int user_id = 0;
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewUser " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);


            int user = Common.inputIntParaNullCheck(req,"user");
            int customer_id = Common.inputIntParaNullCheck(req,"customer_id");
            int profile_id = Common.inputIntParaNullCheck(req,"profile_id");
            String name = Common.inputStringParaNullCheck(req,"name");
            String address = Common.inputStringParaNullCheck(req,"address");
            String email = Common.inputStringParaNullCheck(req,"email");
            int subscription = Common.inputIntParaNullCheck(req,"subscription");
            int dietPlan = Common.inputIntParaNullCheck(req,"dietPlan");
            boolean diet = false;
            if(dietPlan==1){

                 diet = true;
            }


            Optional<GymUsers> gymUsersList = gymUsersService.findByUser_id(customer_id);
            if(!gymUsersList.isEmpty()){

                GymUsers gymUsers = gymUsersList.get();

                gymUsers.setName(name);
                gymUsers.setAddress(address);
                gymUsers.setProfile(profile_id);
                gymUsers.setEmail(email);
                gymUsers.setSubscription(subscription);
                gymUsers.setDiet(diet);
                gymUsers.setDietplan(dietPlan);
                gymUsers.setUpdatedby(user);
                GymUsers owner = gymUsersService.saveUser(gymUsers);
                if(owner!=null){
                    status = true;
                    statusDesc = "User updated successfully";
                }

            }else{
                statusDesc="User not exist";
            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("user_id",user_id );
        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/loadNotifications")
    public String loadNotifications(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int notification_count = 0;
        JSONObject res = new JSONObject();
        JSONArray payemtnsPending = new JSONArray();
        JSONArray dietPlan = new JSONArray();
        JSONObject notificationJson = new JSONObject();
        try {
            System.out.println("gymOwnerService --> addNewUser " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");


                List<Map<String, Object>> payemntsList= gymUserPaymentsService.getpendingPaymentsList(gym_id,Common.GYM_CUSTOMERS);

                if(!payemntsList.isEmpty()) {

                    for (Map<String, Object> payemnts : payemntsList) {
                        JSONObject payItem = new JSONObject();


                        payItem.put("id", payemnts.get("id"));
                        payItem.put("name", payemnts.get("name"));
                        payemtnsPending.put(payItem);
                    }
                }

                notificationJson.put("payemntPending", payemtnsPending);
                notificationJson.put("payemntPendingCount", payemtnsPending.length());

                List<GymUsers> dietUsers = gymUsersService.findCustomerYetToAddDietPlan(gym_id);
                for(GymUsers user : dietUsers){

                    JSONObject userJson = new JSONObject();
                    userJson.put("name",user.getName());
                    userJson.put("id",user.getId());
                    userJson.put("phone",user.getPhone());
                    dietPlan.put(userJson);
                }
                notificationJson.put("dietPlan", dietPlan);
                notificationJson.put("dietPlanPendingCount", dietPlan.length());

                status = true;
                statusDesc="Data fetched successfully";

            notification_count = dietPlan.length()+payemtnsPending.length();


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("notificationJson",notificationJson );
            res.put("notification_count",notification_count );

        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/verifyToken")
    public String verifyToken(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        String phone = "";
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewUser " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);



            String token = Common.inputStringParaNullCheck(req,"token");


            GymUsers gymUsers = new GymUsers();

            gymUsers.setUniquetoken(token);

            GymUsers owner = gymUsersService.verifyToken(gymUsers);

            if (owner != null){

                if(owner.getUniquetoken().length()!=0){

                    statusDesc = "User   exist";
                    phone = owner.getPhone();
                    status = true;
                }else{

                    statusDesc = "User not  exist";
                }
            }else{

                statusDesc = "User not  exist";
            }



        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("phone",phone );
        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/registerToken")
    public String registerToken(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";

        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewUser " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);



            String token = Common.inputStringParaNullCheck(req,"token");
            String phone = Common.inputStringParaNullCheck(req,"phone");
            String password = Common.inputStringParaNullCheck(req,"password");

            if (phone!=null && phone.length()!=0){

                if(phone.substring(0,2).equals("91")){

                    phone = phone.substring(2,phone.length());
                }


                GymUsers gymUsers = new GymUsers();

                gymUsers.setUniquetoken(token);
                gymUsers.setPhone(phone);
                GymUsers owner = gymUsersService.verifyTokenAndPhone(gymUsers);

                if (owner != null){

                    if(owner.getUniquetoken().length()!=0){


                        int updated = gymUsersService.regToken(phone,password);
                        if(updated!=0){

                            status = true;
                            statusDesc = "User registered successfully";
                        }

                    }else{

                        statusDesc = "User not  exist";
                    }
                }else{

                    statusDesc = "User not  exist";
                }


            }else{

                statusDesc = "User not  exist";
            }



        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/phonenoAvailabilityCheck")
    public String usernameAvailabilityCheck(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int user_id = 0;
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewUser " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);
           // String gym_id_str = req.get("gym_id").toString();
            //String password = req.get("password").toString();
            //String address = req.get("address").toString();
            //String ph = req.get("phone").toString();
           // String email = req.get("email").toString();


            String phone = Common.inputStringParaNullCheck(req,"phone");



            GymUsers gymUsers = new GymUsers();

            gymUsers.setPhone(phone);

            gymUsers.setActive(true);

            GymUsers owner = gymUsersService.findUserExist(gymUsers);
            GymUsers gymUsers1 = new GymUsers();
            if (owner == null){


                    statusDesc = "User not  available";
                    status = true;
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
    @PostMapping("/addNewUserProfileByAdmin")
    public String addNewUserProfile(@RequestBody String jsonReq) {

        /*Boolean status = false;
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

         */

        Boolean status = false;
        String statusDesc = "Failed";
        int profile_id = 0;
        JSONObject res = new JSONObject();

        try {
            JSONObject req = new JSONObject(jsonReq);
            String profile_name = req.get("profile_name").toString();
            int gym_id = 0;
            profile_name = profile_name.toUpperCase();
            List<Map<String, Object>> extProfile = gymProfilesService.checkProfileExist(profile_name,gym_id);
            if(extProfile.isEmpty()) {
                GymProfiles profile = new GymProfiles();
                profile.setStatus(true);
                profile.setProfile_name(profile_name);
                profile.setGym_id(gym_id);
                GymProfiles respProfile =gymProfilesService.saveNewProfile(profile);
                if(respProfile != null){
                    System.out.println("gymExpenseListService :: " + respProfile.getId());
                    statusDesc = "Profile added successfully";
                    status=true;
                    profile_id = respProfile.getId();
                }else{

                    statusDesc = "Operation failed";
                }


            }else {

                statusDesc = "Same profile already exists";

            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("profile_id",profile_id );
        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/addNewDietPlan")
    public String addNewDietPlan(@RequestBody String jsonReq) {


        Boolean status = false;
        String statusDesc = "Failed";
        int diet_id = 0;
        JSONObject res = new JSONObject();

        try {
            JSONObject req = new JSONObject(jsonReq);


            int gym_id =Common.inputIntParaNullCheck(req,"gym_id");
            int addedBy =Common.inputIntParaNullCheck(req,"addedBy");
            String dietDetails =Common.inputStringParaNullCheck(req,"dietDetails");
            String dietname =Common.inputStringParaNullCheck(req,"dietname");


            if(dietDetails.length()!=0){

                dietDetails = dietDetails.toUpperCase();
            }

            List<GymDietPlans> extDiet = gymDietPlansService.checkDietExist(dietDetails,gym_id);
            if(extDiet.isEmpty()) {

                GymDietPlans diet = new GymDietPlans();
                diet.setStatus(true);
                diet.setDiet_details(dietDetails);
                diet.setGymid(gym_id);
                diet.setAddedby(addedBy);
                diet.setDiet_name(dietname);
                GymDietPlans newDiet = gymDietPlansService.saveDiet(diet);
                if(newDiet != null){

                    status=true;
                    statusDesc = "Diet added successfully";
                }else{
                    statusDesc = "Operation failed";
                }

            }else {

                statusDesc = "Same diet already exists";

            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("diet_id",diet_id );
        }
        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/findDietPlans")
    public String findDietPlans(@RequestBody String jsonReq) {


        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray dietPlans  = new JSONArray();
        JSONObject res = new JSONObject();

        try {
            JSONObject req = new JSONObject(jsonReq);


            int gym_id =Common.inputIntParaNullCheck(req,"gym_id");
            int offset =Common.inputIntParaNullCheck(req,"offset");


            List<Map<String, Object>> extDiet = gymDietPlansService.findDietPlans(gym_id,offset);
            if(!extDiet.isEmpty()) {

                for(Map<String, Object> diet:extDiet) {
                    JSONObject dietJson = new JSONObject();
                    Object diet_id = ((diet.get("id") == null) ? "0" : diet.get("id")).toString();
                    Object details = ((diet.get("details") == null) ? "" : diet.get("details")).toString();
                    Object created = ((diet.get("created") == null) ? "" : diet.get("created")).toString();
                    Object added = ((diet.get("added") == null) ? "" : diet.get("added")).toString();
                    Object dietname = ((diet.get("dietname") == null) ? "" : diet.get("dietname")).toString();
                    dietJson.put("diet_details", details);
                    dietJson.put("diet_id", diet_id);
                    dietJson.put("addedby", added);
                    dietJson.put("created", created);
                    dietJson.put("dietname", dietname);
                    dietPlans.put(dietJson);
                }
                status=true;
                statusDesc = "Diet plans fetched successfully";
                /*for(GymDietPlans diet:extDiet){

                    JSONObject dietJson = new JSONObject();
                    dietJson.put("diet_details",diet.getDiet_details());
                    dietJson.put("diet_id",diet.getId());
                    dietJson.put("addedby",diet.getAddedby());
                    dietJson.put("addedon",diet.getCreated());
                    dietPlans.put(dietJson);

                }*/
            }else {

                statusDesc = "No diet plan added so far";

            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("dietPlans",dietPlans );
        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/findDietPlansFull")
    public String findDietPlansFull(@RequestBody String jsonReq) {


        Boolean status = false;
        String statusDesc = "Failed";
        JSONArray dietPlans  = new JSONArray();
        JSONObject res = new JSONObject();

        try {
            JSONObject req = new JSONObject(jsonReq);


            int gym_id =Common.inputIntParaNullCheck(req,"gym_id");


            List<Map<String, Object>> extDiet = gymDietPlansService.findDietPlansFull(gym_id);
            if(!extDiet.isEmpty()) {

                for(Map<String, Object> diet:extDiet) {
                    JSONObject dietJson = new JSONObject();
                    Object diet_id = ((diet.get("id") == null) ? "0" : diet.get("id")).toString();
                    Object details = ((diet.get("details") == null) ? "" : diet.get("details")).toString();
                    Object created = ((diet.get("created") == null) ? "" : diet.get("created")).toString();
                    Object added = ((diet.get("added") == null) ? "" : diet.get("added")).toString();
                    Object dietname = ((diet.get("dietname") == null) ? "" : diet.get("dietname")).toString();
                    dietJson.put("diet_details", details);
                    dietJson.put("diet_id", diet_id);
                    dietJson.put("addedby", added);
                    dietJson.put("created", created);
                    dietJson.put("dietname", dietname);
                    dietPlans.put(dietJson);
                }
                status=true;
                statusDesc = "Diet plans fetched successfully";
                /*for(GymDietPlans diet:extDiet){

                    JSONObject dietJson = new JSONObject();
                    dietJson.put("diet_details",diet.getDiet_details());
                    dietJson.put("diet_id",diet.getId());
                    dietJson.put("addedby",diet.getAddedby());
                    dietJson.put("addedon",diet.getCreated());
                    dietPlans.put(dietJson);

                }*/
            }else {

                statusDesc = "No diet plan added so far";

            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("dietPlans",dietPlans );
        }
        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/delDietPlans")
    public String delDietPlans(@RequestBody String jsonReq) {


        Boolean status = false;
        String statusDesc = "Failed";
        JSONObject res = new JSONObject();

        try {
            JSONObject req = new JSONObject(jsonReq);


            int diet_id =Common.inputIntParaNullCheck(req,"diet_id");
            int user =Common.inputIntParaNullCheck(req,"user");


            Integer delCount = gymDietPlansService.delDietPlan(diet_id,user);
            if(delCount!=0) {


                status=true;
                statusDesc = "Diet plan deleted successfully";

            }else {

                statusDesc="Action failed";

            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
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


            String gym_name =Common.inputStringParaNullCheck(req,"gym_name");
            String address =Common.inputStringParaNullCheck(req,"address");
            String description =Common.inputStringParaNullCheck(req,"description");
            int user_id =Common.inputIntParaNullCheck(req,"user_id");


            GymList gymList = new GymList();
            gymList.setDescription(description);
            gymList.setName(gym_name);
            gymList.setGym_name(gym_name);
            gymList.setAdress(address);
            gymList.setCreated_by(user_id);
            gymList.setActive(true);
            GymList gym = gymListService.saveGym(gymList);
            if (gym != null){

                    System.out.println("gymExpenseListService :: " + gym.getId());
                    statusDesc = "GYM created successfully";
                    gym_id = gym.getId();

                    List<GymProfiles> profiles = gymProfilesService.findAllProfiles();
                    if(!profiles.isEmpty() && gym_id!=0){

                        for(GymProfiles profile : profiles){

                            for (String access:Common.access_list) {
                                GymWebAccess webAccess = new GymWebAccess();
                                webAccess.setProfile(profile.getId());
                                webAccess.setGymid(gym_id);
                                webAccess.setAccess(access);
                                webAccess.setStatus(true);
                                gymWebAccessService.saveProfile(webAccess);
                            }

                        }

                    }

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
    @PostMapping("/addNewProfile")
    public String addNewProfile(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int profile_id = 0;
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewGym " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            String gym_id_str = req.get("gym_id").toString();
            String profile_name = req.get("profile_name").toString();
            String added_id_str = req.get("user_id").toString();
            int user_id = Integer.valueOf(added_id_str);
            int gym_id = Integer.valueOf(gym_id_str);

            List<Map<String, Object>> extProfile = gymProfilesService.checkProfileExist(profile_name,gym_id);
            if(extProfile.isEmpty()) {
                GymProfiles profile = new GymProfiles();
                profile.setStatus(true);
                profile.setProfile_name(profile_name);
                profile.setGym_id(gym_id);
                GymProfiles respProfile =gymProfilesService.saveNewProfile(profile);
                if(respProfile != null){
                    System.out.println("gymExpenseListService :: " + respProfile.getId());
                    statusDesc = "Profile added successfully";
                    status=true;
                    profile_id = respProfile.getId();
                }else{

                    statusDesc = "Operation failed";
                }


            }else {

                statusDesc = "Same profile already exists";

            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("profile_id",profile_id );
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
            /*String gym_id_str = req.get("gym_id").toString();
            String from = req.get("from").toString();
            String to = req.get("to").toString();
            String type_str = req.get("type").toString();
            String offset_str = req.get("offset").toString();*/
            int gym_id =Common.inputIntParaNullCheck(req,"gym_id");
            String from =Common.inputStringParaNullCheck(req,"from");
            String to =Common.inputStringParaNullCheck(req,"to");
            int type =Common.inputIntParaNullCheck(req,"type");
            int offset =Common.inputIntParaNullCheck(req,"offset");

            List<Map<String, Object>> expenseList = new ArrayList<>();



           if(from.length()!=0 & to.length()!=0 & type!=0){

               expenseList= gymExpenseListService.getGymExpenseListQueryFilterDateAndType(gym_id,type,new Timestamp(DATE_TIME_FORMAT.parse(from).getTime()),new Timestamp(DATE_TIME_FORMAT.parse(to).getTime()),10,offset);
           }else if(from.length()==0 && to.length()==0 & type!=0){

               expenseList= gymExpenseListService.getGymExpenseListQueryFilterType(gym_id,type,10,offset);
           }else if(from.length()!=0 && to.length()!=0 & type==0){
               expenseList= gymExpenseListService.getGymExpenseListQueryFilterDate(gym_id,new Timestamp(DATE_TIME_FORMAT.parse(from).getTime()),new Timestamp(DATE_TIME_FORMAT.parse(to).getTime()),10,offset);
           }else{

               expenseList= gymExpenseListService.getGymExpenseListQuery(gym_id,10,offset);
           }



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
                    profile.put("phone",gymUsers.getPhone());
                    profile.put("email",gymUsers.getEmail());
                   // SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
                    //Date date2 = dateFormat.parse(gymUsers.getCreated().toString());
                    //Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(gymUsers.getCreated().toString());
                    //String date2 =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(gymUsers.getCreated().toString());


                    profile.put("created",date);
                    Optional<GymList> gymList = gymListService.findById(gymUsers.getGym());
                    if(gymList.isPresent()){

                        if(gymList.get()!=null){

                            profile.put("gym",gymList.get().getGym_name());
                            profile.put("gymaddress",gymList.get().getAdress());
                        }
                    }

                    Optional<GymProfiles> profiles = gymProfilesService.findById(gymUsers.getProfile());
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

    @CrossOrigin
    @PostMapping("/loadGymCustomers")

    public String loadGymCustomers(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        JSONArray profile =new JSONArray();

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> loadProfile "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int offset = Common.inputIntParaNullCheck(req,"offset");
            String phone = Common.inputStringParaNullCheck(req,"phone");
            List<Map<String, Object>> gymUsersList = null;
            if(phone.length()!=0){

                gymUsersList = gymUsersService.findCustomersWithPhone(gym_id, offset,phone);
            }else {
                gymUsersList = gymUsersService.findCustomers(gym_id, Common.GYM_CUSTOMERS, offset);
            }
            if(!gymUsersList.isEmpty()){

                for(Map<String, Object> gymUsers:gymUsersList){

                    JSONObject profileEnt = new JSONObject();
                    profileEnt.put("customer",gymUsers.get("username"));
                    profileEnt.put("name",gymUsers.get("name"));
                    profileEnt.put("phone",gymUsers.get("phone"));
                    profileEnt.put("id",gymUsers.get("id"));
                    profileEnt.put("addedby",gymUsers.get("added"));
                    profileEnt.put("addedOn",gymUsers.get("created"));

                   /* profileEnt.put("username",gymUsersList.get().getUsername());
                    profileEnt.put("name",gymUsersList.get().getName());
                    profileEnt.put("phone",gymUsersList.get().getPhone());
                    profileEnt.put("id",gymUsersList.get().getId());
                    profileEnt.put("addedby",gymUsersList.get().getAddedby());
                    profileEnt.put("addedOn",gymUsersList.get().getCreated().toString());*/
                    profile.put(profileEnt);

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
    @CrossOrigin
    @PostMapping("/loadGymCustomersFull")

    public String loadGymCustomersFull(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        JSONArray profile =new JSONArray();

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> loadProfile "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");

            List<Map<String, Object>> gymUsersList = null;

            gymUsersList = gymUsersService.findFullCustomers(gym_id, Common.GYM_CUSTOMERS);

            if(!gymUsersList.isEmpty()){

                for(Map<String, Object> gymUsers:gymUsersList){

                    JSONObject profileEnt = new JSONObject();
                    profileEnt.put("customer",gymUsers.get("username"));
                    profileEnt.put("name",gymUsers.get("name"));
                    profileEnt.put("phone",gymUsers.get("phone"));
                    profileEnt.put("id",gymUsers.get("id"));
                    profileEnt.put("addedby",gymUsers.get("added"));
                    profileEnt.put("addedOn",gymUsers.get("created"));
                    profileEnt.put("subscription",gymUsers.get("subscription"));

                   /* profileEnt.put("username",gymUsersList.get().getUsername());
                    profileEnt.put("name",gymUsersList.get().getName());
                    profileEnt.put("phone",gymUsersList.get().getPhone());
                    profileEnt.put("id",gymUsersList.get().getId());
                    profileEnt.put("addedby",gymUsersList.get().getAddedby());
                    profileEnt.put("addedOn",gymUsersList.get().getCreated().toString());*/
                    profile.put(profileEnt);

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
    @CrossOrigin
    @PostMapping("/mapPT")

    public String mapPT(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        int updated = 0;

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> loadProfile "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int customer = Common.inputIntParaNullCheck(req,"customer");
            int pt = Common.inputIntParaNullCheck(req,"pt");
            int userid = Common.inputIntParaNullCheck(req,"userid");


            Optional<GymUsers> customer_exist=  gymUsersService.findByUser_id(customer);

            if(!customer_exist.isEmpty()){

                 updated = gymUsersService.mapPT(gym_id,customer,pt,userid);
                if(updated!=0){

                    statusDesc ="Mapped Successfully";
                    status=true;
                }else{
                    statusDesc ="Operation failed";
                }


            }else{
                statusDesc = "Wrong user";
            }




        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
        }

        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/mapSubscription")

    public String mapSubscription(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        int updated = 0;

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> loadProfile "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int customer = Common.inputIntParaNullCheck(req,"customer");
            int sub = Common.inputIntParaNullCheck(req,"sub");
            int userid = Common.inputIntParaNullCheck(req,"userid");


            if(sub!=0 && customer!=0){

                Optional<GymUsers> customer_exist=  gymUsersService.findByUser_id(customer);

                if(!customer_exist.isEmpty()){

                     updated = gymUsersService.mapSub(gym_id,customer,sub,userid);
                    if(updated!=0){

                        statusDesc ="Mapped Successfully";
                        status=true;
                    }else{
                        statusDesc ="Operation failed";
                    }


                }else{
                    statusDesc = "Wrong user";
                }
            }else{
                statusDesc = "Mandatory fields are missing";
            }




        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
        }

        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/mapDietPlan")

    public String mapDietPlan(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        int updated = 0;

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> loadProfile "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int customer = Common.inputIntParaNullCheck(req,"customer");
            int diet = Common.inputIntParaNullCheck(req,"diet");
            int userid = Common.inputIntParaNullCheck(req,"userid");


            if(diet!=0 && customer!=0){

                Optional<GymUsers> customer_exist=  gymUsersService.findByUser_id(customer);

                if(!customer_exist.isEmpty()){

                     updated = gymUsersService.mapDiet(gym_id,customer,diet,userid);
                    if(updated!=0){

                        statusDesc ="Mapped Successfully";
                        status=true;
                    }else{
                        statusDesc ="Operation failed";
                    }


                }else{
                    statusDesc = "Wrong user";
                }
            }else{
                statusDesc = "Mandatory fields are missing";
            }




        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
        }

        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/loadGymCustomersPTFull")

    public String loadGymCustomersPTFull(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        JSONArray profileCust =new JSONArray();
        JSONArray profilePT =new JSONArray();

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> loadProfile "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");

            List<Map<String, Object>> gymUsersList = null;

            gymUsersList = gymUsersService.findFullCustomersPT(gym_id, Common.GYM_CUSTOMERS,Common.GYM_PT);

            if(!gymUsersList.isEmpty()){

                for(Map<String, Object> gymUsers:gymUsersList){

                    String profile_user = ((gymUsers.get("profile") == null) ? "" : gymUsers.get("profile")).toString();
                    JSONObject profileEnt = new JSONObject();
                    profileEnt.put("customer",gymUsers.get("username"));
                    profileEnt.put("customer",((gymUsers.get("username") == null) ? "" : gymUsers.get("username")).toString());
                    profileEnt.put("name",((gymUsers.get("name") == null) ? "" : gymUsers.get("name")).toString());
                    profileEnt.put("phone",((gymUsers.get("phone") == null) ? "" : gymUsers.get("phone")).toString());
                    profileEnt.put("id",((gymUsers.get("id") == null) ? "" : gymUsers.get("id")).toString());
                    profileEnt.put("addedby",((gymUsers.get("addedby") == null) ? "" : gymUsers.get("addedby")).toString());
                    profileEnt.put("addedOn",((gymUsers.get("addedOn") == null) ? "" : gymUsers.get("addedOn")).toString());
                    profileEnt.put("profile_user",profile_user);

                    if(profile_user.equalsIgnoreCase(String.valueOf(Common.GYM_CUSTOMERS))){




                        profileCust.put(profileEnt);
                    }else if(profile_user.equalsIgnoreCase(String.valueOf(Common.GYM_PT))){


                        profilePT.put(profileEnt);
                    }




                }

            }
            statusDesc = "Data fetched";
            status=true;


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("profileCust",profileCust );
            res.put("profilePT",profilePT );
        }

        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/loadGymCustomersNotAddedDiet")

    public String loadGymCustomersNotAddedDiet(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        JSONArray profileCust =new JSONArray();
        JSONArray profilePT =new JSONArray();

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> loadProfile "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");

            List<GymUsers> gymUsersList = null;

            gymUsersList = gymUsersService.findCustomerYetToAddDietPlan(gym_id);

            if(!gymUsersList.isEmpty()){

                for(GymUsers gymUsers:gymUsersList){

                    JSONObject profileEnt = new JSONObject();
                    profileEnt.put("customer",gymUsers.getName());
                    profileEnt.put("name",gymUsers.getName());
                    profileEnt.put("phone",gymUsers.getPhone());
                    profileEnt.put("id",gymUsers.getId());
                    profileEnt.put("addedby",gymUsers.getAddedby());
                    profileEnt.put("addedOn",gymUsers.getCreated());
                    profileEnt.put("profile_user",gymUsers.getProfile());
                     profileCust.put(profileEnt);





                }

            }
            statusDesc = "Data fetched";
            status=true;


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("profileCust",profileCust );
            res.put("profilePT",profilePT );
        }

        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/loadGymMembers")

    public String loadGymMembers(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";


        JSONArray profile =new JSONArray();

        JSONObject res = new JSONObject();
        try{

            System.out.println("gymOwnerService --> loadProfile "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            int gym_id = Integer.valueOf(gym_id_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int profile_id = Common.inputIntParaNullCheck(req,"profile");
            int offset = Common.inputIntParaNullCheck(req,"offset");

            String phone = Common.inputStringParaNullCheck(req,"phone");
            List<Map<String, Object>> gymUsersList = null;
            if(phone.length()!=0){

                gymUsersList = gymUsersService.findCustomersWithPhone(gym_id, 0,phone);
            }else {
                gymUsersList = gymUsersService.findCustomers(gym_id, profile_id, offset);
            }
            if(!gymUsersList.isEmpty()){

                for(Map<String, Object> gymUsers:gymUsersList){

                    JSONObject profileEnt = new JSONObject();
                    /*profileEnt.put("username",gymUsers.get("username"));
                    profileEnt.put("name",gymUsers.get("name"));
                    profileEnt.put("phone",gymUsers.get("phone"));
                    profileEnt.put("id",gymUsers.get("id"));
                    profileEnt.put("addedby",gymUsers.get("added"));
                    profileEnt.put("addedOn",gymUsers.get("created"));
                    profileEnt.put("address",gymUsers.get("address"));*/
                    profileEnt.put("username",((gymUsers.get("username") == null) ? "" : gymUsers.get("username")).toString());
                    profileEnt.put("name",((gymUsers.get("name") == null) ? "" : gymUsers.get("name")).toString());
                    profileEnt.put("phone",((gymUsers.get("phone") == null) ? "" : gymUsers.get("phone")).toString());
                    profileEnt.put("id",((gymUsers.get("id") == null) ? "" : gymUsers.get("id")).toString());
                    profileEnt.put("addedby",((gymUsers.get("added") == null) ? "" : gymUsers.get("added")).toString());
                    profileEnt.put("addedOn",((gymUsers.get("created") == null) ? "" : gymUsers.get("created")).toString());
                    profileEnt.put("address",((gymUsers.get("address") == null) ? "Not Available" : gymUsers.get("address")).toString());
                    profileEnt.put("subscription",((gymUsers.get("description") == null) ? "Not Added" : gymUsers.get("description")).toString());
                    profileEnt.put("subscriptionId",((gymUsers.get("subscription") == null) ? "0" : gymUsers.get("subscription")).toString());
                    profileEnt.put("dietname",((gymUsers.get("dietname") == null) ? "Not Added" : gymUsers.get("dietname")).toString());
                    profileEnt.put("dietplan",((gymUsers.get("dietplan") == null) ? "0" : gymUsers.get("dietplan")).toString());
                    profileEnt.put("email",((gymUsers.get("email") == null) ? "" : gymUsers.get("email")).toString());
                    profileEnt.put("profile",((gymUsers.get("profile") == null) ? "0" : gymUsers.get("profile")).toString());



                   /* profileEnt.put("username",gymUsersList.get().getUsername());
                    profileEnt.put("name",gymUsersList.get().getName());
                    profileEnt.put("phone",gymUsersList.get().getPhone());
                    profileEnt.put("id",gymUsersList.get().getId());
                    profileEnt.put("addedby",gymUsersList.get().getAddedby());
                    profileEnt.put("addedOn",gymUsersList.get().getCreated().toString());*/
                    profile.put(profileEnt);

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

    @CrossOrigin
    @PostMapping("/loadCustomerPayments")

    public String loadCustomerPayments(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";

        JSONArray paymentsJson =new JSONArray();
        DecimalFormat formatter
                = new DecimalFormat("₹#,##0.00");
        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            String offset_str = req.get("offset").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            int offset = Integer.valueOf(offset_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int offset = Common.inputIntParaNullCheck(req,"offset");
            int year = Common.inputIntParaNullCheck(req,"year");

            List<Map<String, Object>> paymentsList= gymUserPaymentsService.getGymPayments(gym_id,year,offset);

            if(!paymentsList.isEmpty()){

                for(Map<String, Object> pay:paymentsList){
                    JSONObject expenseItem = new JSONObject();


                    expenseItem.put("customer",pay.get("customer"));
                    expenseItem.put("description",pay.get("description"));
                    expenseItem.put("subscription",pay.get("subscription"));
                    expenseItem.put("amount",pay.get("amount"));
                    expenseItem.put("createdon",pay.get("createdon"));
                    expenseItem.put("id",pay.get("id"));
                    expenseItem.put("name",pay.get("name"));
                    expenseItem.put("paymonth",pay.get("paymonth"));
                    expenseItem.put("payyear",pay.get("payyear"));



                    paymentsJson.put(expenseItem);
                }

            }
            statusDesc = "Data fetched";
            status=true;


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("payList",paymentsJson );
        }

        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/loadCustomerPaymentsByCustid")

    public String loadCustomerPaymentsByCustid(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";

        JSONArray paymentsJson =new JSONArray();
        DecimalFormat formatter
                = new DecimalFormat("₹#,##0.00");
        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            String offset_str = req.get("offset").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            int offset = Integer.valueOf(offset_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int offset = Common.inputIntParaNullCheck(req,"offset");
            int customer = Common.inputIntParaNullCheck(req,"customer");

            List<Map<String, Object>> paymentsList= gymUserPaymentsService.getGymPaymentsCustomer(customer,offset);

            if(!paymentsList.isEmpty()){

                for(Map<String, Object> pay:paymentsList){
                    JSONObject expenseItem = new JSONObject();



                    String endDate = pay.get("topaydate").toString();
                    if (new SimpleDateFormat("yyyy-MM-dd").parse(endDate).before(new Date())) {

                        expenseItem.put("expired","Expired");

                    }else{

                        expenseItem.put("expired","Active");
                    }
                    expenseItem.put("customer",pay.get("customer"));
                    expenseItem.put("finalamount",pay.get("finalamount"));
                    expenseItem.put("discountadded",pay.get("discountadded"));
                    expenseItem.put("description",pay.get("description"));
                    expenseItem.put("subscription",pay.get("subscription"));
                    expenseItem.put("amount",pay.get("amount"));
                    expenseItem.put("createdon",pay.get("createdon"));
                    expenseItem.put("id",pay.get("id"));
                    expenseItem.put("name",pay.get("name"));
                    expenseItem.put("paymonth",pay.get("paymonth"));
                    expenseItem.put("payyear",pay.get("payyear"));
                    expenseItem.put("toDate",pay.get("topaydate"));
                    expenseItem.put("strDate",pay.get("frompaydate"));




                    paymentsJson.put(expenseItem);
                }

            }
            statusDesc = "Data fetched";
            status=true;


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("payList",paymentsJson );
        }

        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/loadCustomerPaymentsByid")

    public String loadCustomerPaymentsByid(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";

        JSONObject paymentsJson =new JSONObject();
        DecimalFormat formatter
                = new DecimalFormat("₹#,##0.00");
        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            String offset_str = req.get("offset").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            int offset = Integer.valueOf(offset_str);*/
            int payment_id = Common.inputIntParaNullCheck(req,"payment_id");


            List<Map<String, Object>> paymentsList= gymUserPaymentsService.findCustomerPaymentById(payment_id);

            if(!paymentsList.isEmpty()){

                for(Map<String, Object> pay:paymentsList){



                    String endDate = pay.get("topaydate").toString();
                    if (new SimpleDateFormat("yyyy-MM-dd").parse(endDate).before(new Date())) {

                        paymentsJson.put("expired","Expired");

                    }else{

                        paymentsJson.put("expired","Active");
                    }
                    paymentsJson.put("customer",pay.get("customer"));
                    paymentsJson.put("id",pay.get("id"));
                    paymentsJson.put("description",pay.get("description"));
                    paymentsJson.put("amount",pay.get("amount"));
                    paymentsJson.put("subscription",pay.get("subscription"));
                    paymentsJson.put("finalamount",pay.get("finalamount"));
                    paymentsJson.put("frompaydate",pay.get("frompaydate"));
                    paymentsJson.put("topaydate",pay.get("topaydate"));
                    paymentsJson.put("discountadded",pay.get("discountadded"));





                }

            }
            statusDesc = "Data fetched";
            status=true;


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("payList",paymentsJson );
        }

        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/loadGymEnq")

    public String loadGymEnq(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";

        JSONArray enqJson =new JSONArray();

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            /*String gym_id_str = req.get("gym_id").toString();
            String offset_str = req.get("offset").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            int offset = Integer.valueOf(offset_str);*/
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int offset = Common.inputIntParaNullCheck(req,"offset");

            List<Map<String, Object>> qryList= gymQueriesService.getQueries(gym_id,offset);

            if(!qryList.isEmpty()){

                for(Map<String, Object> qry:qryList){
                    JSONObject enqItem = new JSONObject();

                    enqItem.put("id",((qry.get("id") == null) ? "" : qry.get("id")).toString());
                    enqItem.put("phone",((qry.get("phone") == null) ? "" : qry.get("phone")).toString());
                    enqItem.put("query",((qry.get("query") == null) ? "" : qry.get("query")).toString());
                    enqItem.put("added",((qry.get("added") == null) ? "" : qry.get("added")).toString());
                    enqItem.put("addedOn",((qry.get("addedOn") == null) ? "" : qry.get("addedOn")).toString());






                    enqJson.put(enqItem);
                }

            }
            statusDesc = "Data fetched";
            status=true;


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("enqJson",enqJson );
        }

        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/addGymEnq")

    public String addGymEnq(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";

        int queryId = 0;

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int phone = Common.inputIntParaNullCheck(req,"phone");
            int added = Common.inputIntParaNullCheck(req,"added");
            String query = Common.inputStringParaNullCheck(req,"query");

            GymQueries queries  = new GymQueries();
            queries.setQuery(query);
            queries.setPhone(phone);
            queries.setAdded(added);
            queries.setStatus(true);
            queries.setGymid(gym_id);


            GymQueries newquery = gymQueriesService.save(queries);

            if(newquery!=null){

                queryId = newquery.getId();
                statusDesc = "Data Added Successfully";
                status=true;
            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("queryId",queryId );
        }

        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/delGymEnq")

    public String delGymEnq(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";

        int queryId = 0;

        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int qry_id = Common.inputIntParaNullCheck(req,"qry_id");
            int user = Common.inputIntParaNullCheck(req,"user");

            int del = gymQueriesService.disableQueries(qry_id,user);

            if(del!=0){


                statusDesc = "Data deleted Successfully";
                status=true;
            }else{
                statusDesc = "Data not deleted";
            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("queryId",queryId );
        }

        return res.toString();



    }


    @CrossOrigin
    @PostMapping("/loadExpSpark")

    public String loadExpSpark(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";

        JSONArray expData = new JSONArray();
        Hashtable<String,Float > expTable = new Hashtable<String,Float>();
        DecimalFormat formatter
                = new DecimalFormat("₹#,##0.00");
        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);

            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            LocalDate todaydate = LocalDate.now();


            List<Map<String, Object>> expenseList= gymExpenseListService.getExpenseChart(gym_id,new Timestamp(DATE_TIME_FORMAT.parse(todaydate.withDayOfMonth(1).toString()).getTime()));


            if(!expenseList.isEmpty()){

                for(Map<String, Object> expense:expenseList) {
                    JSONObject expenseItem = new JSONObject();
                    String expitem = ((expense.get("expitem") == null) ? "" : expense.get("expitem")).toString();
                    String amt = ((expense.get("amt") == null) ? "0" : expense.get("amt")).toString();
                    //String currStr = formatter.format(amt);
                    Float amount = Float.valueOf(amt);
                    if(expTable.get(expitem)==null){

                        expTable.put(expitem,amount);
                    }else{


                        Float amount_sum = expTable.get(expitem);
                        amount_sum=amount_sum+amount;
                        expTable.put(expitem,amount_sum);
                    }


                }


                Set<String> keys = expTable.keySet();
                for(String key: keys){
                    Float amount = expTable.get(key);
                    JSONObject expenseItem = new JSONObject();
                    expenseItem.put("expItem",key);
                    expenseItem.put("amount",amount);
                    expData.put(expenseItem);

                }

                status = true;
                statusDesc="Data fetched";

            }else{

                statusDesc="No data";
            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("expData",expData );
        }

        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/loadCustomerPaymentsFilter")

    public String loadCustomerPaymentsFilter(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";

        JSONArray paymentsJson =new JSONArray();
        DecimalFormat formatter
                = new DecimalFormat("₹#,##0.00");
        JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            /*String offset_str = req.get("offset").toString();
            String offset_str = req.get("offset").toString();
            String offset_str = req.get("offset").toString();
            int gym_id = Integer.valueOf(gym_id_str);
            int offset = Integer.valueOf(offset_str);*/
            int offset = Common.inputIntParaNullCheck(req,"offset");
            int month = Common.inputIntParaNullCheck(req,"month");
            int year = Common.inputIntParaNullCheck(req,"year");
            int customer = Common.inputIntParaNullCheck(req,"customer");

            String queryDate ="";
            if(year!=0 && month!=0){

                queryDate =year+"-"+month+"-01";
            }else{

                Calendar c = Calendar.getInstance();
                int year1 = c.get(Calendar.YEAR);
                year = year1;
                int month1 = c.get(Calendar.MONTH);
                queryDate =year1+"-"+month1+"-01";

            }

            Date fromDate = DATE_TIME_FORMAT.parse(queryDate);

            java.sql.Date qDate = new java.sql.Date(fromDate.getTime());
            if(gym_id!=0 && year!=0 ) {

                List<Map<String, Object>> paymentsList = new ArrayList<>();

                if(customer!=0 ){
                    paymentsList = gymUserPaymentsService.getGymPaymentsFilterCustomerYear(gym_id,qDate,customer,offset);

                }else if(month!=0){
                    paymentsList = gymUserPaymentsService.getGymPaymentsFilterMonthYear(gym_id,qDate,offset);
                }else {
                    paymentsList = gymUserPaymentsService.getGymPayments(gym_id,year, offset);
                }
                if (!paymentsList.isEmpty()) {

                    for (Map<String, Object> pay : paymentsList) {
                        /*JSONObject expenseItem = new JSONObject();


                        expenseItem.put("customer", pay.get("customer"));
                        expenseItem.put("description", pay.get("description"));
                        expenseItem.put("subscription", pay.get("subscription"));
                        expenseItem.put("amount", pay.get("amount"));
                        expenseItem.put("createdon", pay.get("createdon"));
                        expenseItem.put("id", pay.get("id"));
                        expenseItem.put("name", pay.get("name"));
                        expenseItem.put("paymonth", pay.get("paymonth"));
                        expenseItem.put("payyear", pay.get("payyear"));*/

                        JSONObject expenseItem = new JSONObject();

                        String endDate = pay.get("topaydate").toString();
                        if (new SimpleDateFormat("yyyy-MM-dd").parse(endDate).before(new Date())) {

                            expenseItem.put("expired","Expired");

                        }else{

                            expenseItem.put("expired","Active");
                        }
                        expenseItem.put("finalamount",pay.get("finalamount"));
                        expenseItem.put("discountadded",pay.get("discountadded"));
                        expenseItem.put("customer",pay.get("customer"));
                        expenseItem.put("description",pay.get("description"));
                        expenseItem.put("subscription",pay.get("subscription"));
                        expenseItem.put("toDate",pay.get("topaydate"));
                        expenseItem.put("strDate",pay.get("frompaydate"));
                        expenseItem.put("amount",pay.get("amount"));
                        expenseItem.put("createdon",pay.get("createdon"));
                        expenseItem.put("id",pay.get("id"));
                        expenseItem.put("name",pay.get("name"));
                        expenseItem.put("paymonth",pay.get("paymonth"));
                        expenseItem.put("payyear",pay.get("payyear"));



                        paymentsJson.put(expenseItem);
                    }

                    statusDesc = "Data fetched";
                    status = true;
                }

            }else{

                statusDesc = "Mandatory fields missing";
            }


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("payList",paymentsJson );
        }

        return res.toString();



    }
    @CrossOrigin
    @PostMapping("/markCustomerPayments")

    public String markCustomerPayments(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";
        ArrayList<Integer> payIds = new ArrayList<>();
    int paymentid=0;
            JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
           /* String gym_id_str = req.get("gym_id").toString();
            String customer_str = req.get("customer").toString();
            String addedby_str = req.get("addedby").toString();
            String amount_str = req.get("amount").toString();
            String description = req.get("description").toString();
            String subscription = req.get("subscription").toString();

             int gym_id = Integer.valueOf(gym_id_str);
            int customer = Integer.valueOf(customer_str);
            int addedby = Integer.valueOf(addedby_str);
            float amount = Float.valueOf(amount_str);
            */


            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int customer = Common.inputIntParaNullCheck(req,"customer");
            int fromMonth = Common.inputIntParaNullCheck(req,"fromMonth");
            int toMonth = Common.inputIntParaNullCheck(req,"toMonth");
            int fromYear = Common.inputIntParaNullCheck(req,"fromYear");
            int toYear = Common.inputIntParaNullCheck(req,"toYear");
            int addedby = Common.inputIntParaNullCheck(req,"addedby");
            float amount = Common.inputFloatParaNullCheck(req,"amount");
            String description = Common.inputStringParaNullCheck(req,"description");
            int subscription = Common.inputIntParaNullCheck(req,"subscription");

            Calendar calendar = Calendar.getInstance();
            int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
            Calendar cal_end = Calendar.getInstance();
            cal_end.add(Calendar.MONTH, 1);


            boolean sameyear = true;
            boolean diffMonth = true;

            if(fromYear!=toYear){
                sameyear= false;
            }
            if(fromMonth==toMonth && fromYear==toYear){

                diffMonth= false;

            }

            if(!diffMonth){

                GymUserPayments gymUserPayments = new GymUserPayments();
                gymUserPayments.setGym(gym_id);
                gymUserPayments.setCustomer(customer);
                gymUserPayments.setAddedby(addedby);
                gymUserPayments.setAmount(amount);
                gymUserPayments.setDescription(description);
                gymUserPayments.setSubscription(subscription);
                gymUserPayments.setPaymonth(fromMonth);
                gymUserPayments.setPayyear(fromYear);
                gymUserPayments.setStatus(true);
                GymUserPayments newGymUserPayments = new GymUserPayments();
                payIds.add(gymUserPaymentsService.InsertPayments(gymUserPayments).getId());
                status = true;
                 statusDesc = "Added successfully";
            }else if(!sameyear){

                int curr_year = fromYear;
                int startMonth = 0;
                for( startMonth = fromMonth;startMonth<=12;startMonth++){

                    GymUserPayments gymUserPayments = new GymUserPayments();
                    gymUserPayments.setGym(gym_id);
                    gymUserPayments.setCustomer(customer);
                    gymUserPayments.setAddedby(addedby);
                    gymUserPayments.setAmount(amount);
                    gymUserPayments.setDescription(description);
                    gymUserPayments.setSubscription(subscription);
                    gymUserPayments.setPaymonth(startMonth);
                    gymUserPayments.setPayyear(curr_year);
                    gymUserPayments.setStatus(true);
                    GymUserPayments newGymUserPayments = new GymUserPayments();
                    payIds.add(gymUserPaymentsService.InsertPayments(gymUserPayments).getId());
                    status = true;
                    statusDesc = "Added successfully";

                }
                curr_year=curr_year+1;

                for(int endMonth = 1;endMonth<=toMonth;endMonth++){

                    GymUserPayments gymUserPayments = new GymUserPayments();
                    gymUserPayments.setGym(gym_id);
                    gymUserPayments.setCustomer(customer);
                    gymUserPayments.setAddedby(addedby);
                    gymUserPayments.setAmount(amount);
                    gymUserPayments.setDescription(description);
                    gymUserPayments.setSubscription(subscription);
                    gymUserPayments.setPaymonth(endMonth);
                    gymUserPayments.setPayyear(curr_year);
                    gymUserPayments.setStatus(true);
                    GymUserPayments newGymUserPayments = new GymUserPayments();
                    payIds.add(gymUserPaymentsService.InsertPayments(gymUserPayments).getId());


                }

            }else{

                int startMonth = 0;
                for(startMonth=fromMonth;startMonth<=toMonth;startMonth++){

                    GymUserPayments gymUserPayments = new GymUserPayments();
                    gymUserPayments.setGym(gym_id);
                    gymUserPayments.setCustomer(customer);
                    gymUserPayments.setAddedby(addedby);
                    gymUserPayments.setAmount(amount);
                    gymUserPayments.setDescription(description);
                    gymUserPayments.setSubscription(subscription);
                    gymUserPayments.setPaymonth(startMonth);
                    gymUserPayments.setPayyear(toYear);
                    gymUserPayments.setStatus(true);
                    GymUserPayments newGymUserPayments = new GymUserPayments();
                    payIds.add(gymUserPaymentsService.InsertPayments(gymUserPayments).getId());

                }
            }
            status = true;
            statusDesc = "Added successfully";




           /* if(newGymUserPayments!=null){

                paymentid = newGymUserPayments.getId();
                statusDesc = "Data saved";
                status=true;

            }else{

                statusDesc = "Insertion failed";
                status=false;
            }*/



        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("paymentid",payIds );
        }

        return res.toString();



    }

    @CrossOrigin
    @PostMapping("/editGymPayments")
    public String editGymPayments(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        int user_id = 0;
        JSONObject res = new JSONObject();

        try {
            System.out.println("gymOwnerService --> addNewUser " + jsonReq);
            JSONObject req = new JSONObject(jsonReq);


            int payment_id = Common.inputIntParaNullCheck(req,"payment_id");

            Boolean discountAdded = false;

            int duration = Common.inputIntParaNullCheck(req,"duration");
            float amount = Common.inputFloatParaNullCheck(req,"amount");
            float finalamount = Common.inputFloatParaNullCheck(req,"finalamount");
            String description = Common.inputStringParaNullCheck(req,"description");
            String fromdate = Common.inputStringParaNullCheck(req,"fromdate");
            String discount = Common.inputStringParaNullCheck(req,"discount");
            if(discount.equalsIgnoreCase("true")){
                discountAdded=true;
            }
            int subscription = Common.inputIntParaNullCheck(req,"subscription");

            Date fromDate = DATE_TIME_FORMAT.parse(fromdate);
            Date endDate = DATE_TIME_FORMAT.parse(fromdate);
            Calendar from_cal = Calendar.getInstance();
            from_cal.setTime(fromDate);

            endDate.setMonth(endDate.getMonth() + 1);

            int dayOfYear = from_cal.get(Calendar.DAY_OF_YEAR);
            from_cal.add(Calendar.MONTH, duration);
            int toDayOfYear = from_cal.get(Calendar.DAY_OF_YEAR);
            int year = from_cal.get(Calendar.YEAR);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String toPayDate = formatter.format(endDate);



            GymUserPayments gymUserPayments = gymUserPaymentsService.findById(payment_id);
            if(gymUserPayments!=null){


                gymUserPayments.setAmount(amount);
                gymUserPayments.setDescription(description);
                gymUserPayments.setSubscription(subscription);
                gymUserPayments.setFinalamount(finalamount);
                gymUserPayments.setFromdoy(dayOfYear);
                gymUserPayments.setTodoy(toDayOfYear);
                gymUserPayments.setDiscountadded(discountAdded);
                gymUserPayments.setPayyear(year);


                gymUserPayments.setFrompaydate(new java.sql.Date(fromDate.getTime()));
                gymUserPayments.setTopaydate(new java.sql.Date(endDate.getTime()));


                        GymUserPayments newPay = gymUserPaymentsService.InsertPayments(gymUserPayments);
                if(newPay!=null){
                    status = true;
                    statusDesc = "Payments updated successfully";
                }

            }else{
                statusDesc="Payments not exist";
            }





        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
        }
        return res.toString();



    }


    @CrossOrigin
    @PostMapping("/markCustomerPaymentsV2")

    public String markCustomerPaymentsV2(@RequestBody String jsonReq) {

        Boolean status = false;
        String statusDesc = "Failed";
        statusDesc = "Operation failed";
        ArrayList<Integer> payIds = new ArrayList<>();
    int paymentid=0;
            JSONObject res = new JSONObject();
        try{
            System.out.println("gymOwnerService --> addExpense "+jsonReq);
            JSONObject req = new JSONObject(jsonReq);
            Boolean discountAdded = false;

            int gym_id = Common.inputIntParaNullCheck(req,"gym_id");
            int addedby = Common.inputIntParaNullCheck(req,"addedby");
            int customer = Common.inputIntParaNullCheck(req,"customer");
            int duration = Common.inputIntParaNullCheck(req,"duration");
            float amount = Common.inputFloatParaNullCheck(req,"amount");
            float finalamount = Common.inputFloatParaNullCheck(req,"finalamount");
            String description = Common.inputStringParaNullCheck(req,"description");
            String fromdate = Common.inputStringParaNullCheck(req,"fromdate");
            String discount = Common.inputStringParaNullCheck(req,"discount");
            if(discount.equalsIgnoreCase("true")){
                discountAdded=true;
            }
            int subscription = Common.inputIntParaNullCheck(req,"subscription");

            Date fromDate = DATE_TIME_FORMAT.parse(fromdate);
            Date endDate = DATE_TIME_FORMAT.parse(fromdate);
            Calendar from_cal = Calendar.getInstance();
            from_cal.setTime(fromDate);

            endDate.setMonth(endDate.getMonth() + 1);

            int dayOfYear = from_cal.get(Calendar.DAY_OF_YEAR);
            from_cal.add(Calendar.MONTH, duration);
            int toDayOfYear = from_cal.get(Calendar.DAY_OF_YEAR);
            int year = from_cal.get(Calendar.YEAR);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String toPayDate = formatter.format(endDate);



            GymUserPayments gymUserPayments = new GymUserPayments();
            gymUserPayments.setGym(gym_id);
            gymUserPayments.setCustomer(customer);
            gymUserPayments.setAddedby(addedby);
            gymUserPayments.setAmount(amount);
            gymUserPayments.setDescription(description);
            gymUserPayments.setSubscription(subscription);
            gymUserPayments.setFinalamount(finalamount);
            gymUserPayments.setFromdoy(dayOfYear);
            gymUserPayments.setTodoy(toDayOfYear);
            gymUserPayments.setStatus(true);
            gymUserPayments.setDiscountadded(discountAdded);
            gymUserPayments.setPayyear(year);


            gymUserPayments.setFrompaydate(new java.sql.Date(fromDate.getTime()));
            gymUserPayments.setTopaydate(new java.sql.Date(endDate.getTime()));

            payIds.add(gymUserPaymentsService.InsertPayments(gymUserPayments).getId());


            status = true;
            statusDesc = "Added successfully";


        }catch(Exception e){ e.printStackTrace();}finally {
            res.put("status",status);
            res.put("statusDesc",statusDesc);
            res.put("paymentid",payIds );
        }

        return res.toString();



    }



}
