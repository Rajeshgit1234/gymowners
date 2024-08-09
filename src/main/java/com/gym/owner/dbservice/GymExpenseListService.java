package com.gym.owner.dbservice;

import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymExpenseList;
import com.gym.owner.dbrepo.GymExpenseListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymExpenseListService {


    @Autowired
    private GymExpenseListRepo gymExpenseListRepo;

    public List<GymExpenseList> getGymExpenseList(int gym_id) {

       return gymExpenseListRepo.getExpenseList(gym_id);
    }
}
