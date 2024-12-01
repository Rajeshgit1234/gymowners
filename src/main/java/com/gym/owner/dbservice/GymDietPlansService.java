package com.gym.owner.dbservice;

import com.gym.owner.DB.GymDietPlans;
import com.gym.owner.DB.GymUsers;
import com.gym.owner.dbrepo.GymDietPlansRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GymDietPlansService {

    @Autowired
    private GymDietPlansRepo gymDietPlansRepo;


    public GymDietPlans saveDiet(GymDietPlans dietPlans) {

        return  gymDietPlansRepo.save(dietPlans);
    }

    public List<GymDietPlans> checkDietExist(String dietplan, Integer gymId){

        return  gymDietPlansRepo.checkDietExist(dietplan,gymId);
    }
    public List<Map<String, Object>> findDietPlans( Integer gymId,Integer offset){

        return  gymDietPlansRepo.findByGymidAndStatus(gymId,offset);
    }
    public List<Map<String, Object>> findDietPlansFull( Integer gymId){

        return  gymDietPlansRepo.findByGymidAndStatusFull(gymId);
    }

    public Integer delDietPlan( Integer diet,Integer userid){

        return  gymDietPlansRepo.delDietPlan(diet,userid);
    }

}
