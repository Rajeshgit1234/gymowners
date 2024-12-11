package com.gym.owner.dbservice;

import com.gym.owner.DB.GymUserPayments;
import com.gym.owner.dbrepo.GymUserPaymentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GymUserPaymentsService {
    @Autowired
    private GymUserPaymentsRepo gymUserPaymentsRepo;
    public List<GymUserPayments> findAll() {
        return gymUserPaymentsRepo.findAll();
    }

    public List<Map<String, Object>> findCustomerTotalPayments(int customer, int gym, int offset) {
        return gymUserPaymentsRepo.getCustomerPayments(customer,gym,10,offset);
    }
    public List<Map<String, Object>> getGymPayments(int gym, int year,int offset) {
        return gymUserPaymentsRepo.getGymPayments(gym,year,10,offset);
    }
    public List<Map<String, Object>> getGymPaymentsCustomer(int customer, int offset) {
        return gymUserPaymentsRepo.getGymPaymentsCustomer(customer,12,offset);
    }
    public List<Map<String, Object>> getGymPaymentsFilterMonthYear(int gym, java.sql.Date queryDate,int offset) {
        return gymUserPaymentsRepo.getGymPaymentsFilterMonthYear(gym,queryDate,10,offset);
    }

    public List<Map<String, Object>> getPaySumMonth(int gym, int payyear,int paymonth) {
        return gymUserPaymentsRepo.getPaySumMonth(gym,payyear,paymonth);
    }

    public List<Map<String, Object>> getGymPaymentsFilterMonth(int gym, int payyear,int paymonth) {
        return gymUserPaymentsRepo.getGymPaymentsMonth(gym,payyear,paymonth);
    }

    public List<Map<String, Object>> getGymPaymentsFilterYear(int gym, int payyear,int offset) {
        return gymUserPaymentsRepo.getGymPaymentsFilterYear(gym,payyear,10,offset);
    }
    public List<Map<String, Object>> getpendingPaymentsList(int gym, int profile) {
        return gymUserPaymentsRepo.getpendingPaymentsList(gym,profile);
    }
    public List<Map<String, Object>> getGymPaymentsFilterCustomerYear(int gym, java.sql.Date queryDate,int customer, int offset) {
        return gymUserPaymentsRepo.getGymPaymentsFilterCustomerYear(gym,queryDate,customer,10,offset);
    }

    public GymUserPayments InsertPayments(GymUserPayments gymUserPayments) {
        return gymUserPaymentsRepo.save(gymUserPayments);
    }

    public Integer deletePayment(int pay_id,int userid) {
        return gymUserPaymentsRepo.delPay(pay_id,userid);
    }




}
