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
    public List<Map<String, Object>> getGymPayments(int gym, int offset) {
        return gymUserPaymentsRepo.getGymPayments(gym,10,offset);
    }
    public List<Map<String, Object>> getGymPaymentsFilterMonthYear(int gym, int payyear,int paymonth,int offset) {
        return gymUserPaymentsRepo.getGymPaymentsFilterMonthYear(gym,payyear,paymonth,10,offset);
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
    public List<Map<String, Object>> getpendingPaymentsList(int gym, int profile,int year,int doy) {
        return gymUserPaymentsRepo.getpendingPaymentsList(gym,profile,year,doy);
    }
    public List<Map<String, Object>> getGymPaymentsFilterCustomerYear(int gym,int payyear,int customer, int offset) {
        return gymUserPaymentsRepo.getGymPaymentsFilterCustomerYear(gym,payyear,customer,10,offset);
    }

    public GymUserPayments InsertPayments(GymUserPayments gymUserPayments) {
        return gymUserPaymentsRepo.save(gymUserPayments);
    }

    public Integer deletePayment(int pay_id,int userid) {
        return gymUserPaymentsRepo.delPay(pay_id,userid);
    }




}
