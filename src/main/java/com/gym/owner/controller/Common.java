package com.gym.owner.controller;

import org.json.JSONObject;

public class Common {

    public static final int GYM_CUSTOMERS= 253;//based on profile table id
    public static final int GYM_OWNERS= 252;


    public static String  checkNull(String value){
        if(value == null ){
            return "";
        }else{
            return value;
        }
    }
    public static String  inputStringParaNullCheck(JSONObject req,String value){
       String res = "";
        try {
            if (req.get(value) == null) {
                return "";
            } else {
                res =  req.get(value).toString();
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return res;
    }

    public static int  inputIntParaNullCheck(JSONObject req,String value){


        int res = 0;
        try {
            if(req.get(value) == null ){
                res = 0;
            }else{
                String str_val =  req.get(value).toString();
                res =  Integer.parseInt(str_val);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return res;

    }
    public static float  inputFloatParaNullCheck(JSONObject req,String value){


        float res = 0;
        try {
            if(req.get(value) == null ){
                res = 0;
            }else{
                String str_val =  req.get(value).toString();
                res =  Float.parseFloat(str_val);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return res;

    }

}
