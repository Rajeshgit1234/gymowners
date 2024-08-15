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

    public GymUserPayments InsertPayments(GymUserPayments gymUserPayments) {
        return gymUserPaymentsRepo.save(gymUserPayments);
    }


}
