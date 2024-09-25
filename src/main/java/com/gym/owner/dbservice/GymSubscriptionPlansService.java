package com.gym.owner.dbservice;

import com.gym.owner.DB.GymList;
import com.gym.owner.DB.GymSubscriptionPlans;
import com.gym.owner.dbrepo.GymProfilesRepo;
import com.gym.owner.dbrepo.GymSubscriptionPlansRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GymSubscriptionPlansService {

    @Autowired
    private GymSubscriptionPlansRepo gymSubscriptionPlansRepo;

    public List<Map<String, Object>> getPlans(Integer gymId, int offset){

        return  gymSubscriptionPlansRepo.getPlans(gymId,offset);
    }
    public List<GymSubscriptionPlans> getPlansFull(Integer gymId){

        return  gymSubscriptionPlansRepo.getPlansFull(gymId);
    }
    public List<GymSubscriptionPlans> getPlansFullById(Integer gymId,Integer subId){

        return  gymSubscriptionPlansRepo.getPlansFullById(gymId,subId);
    }

    public GymSubscriptionPlans checkIfExist(GymSubscriptionPlans gymSubscriptionPlans){

        return  gymSubscriptionPlansRepo.checkIfExist(gymSubscriptionPlans.getGym(),gymSubscriptionPlans.getDescription().toUpperCase());
    }

    public GymSubscriptionPlans save(GymSubscriptionPlans gymSubscriptionPlans){
        return gymSubscriptionPlansRepo.save(gymSubscriptionPlans);
    }

    public int delSubscription(Integer id){
        return gymSubscriptionPlansRepo.delSubscription(id);
    }


}
