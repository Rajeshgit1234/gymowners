package com.gym.owner.dbservice;

import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymExpenseList;
import com.gym.owner.DB.GymExpenseListQuery;
import com.gym.owner.dbrepo.GymExpenseListQueryRepo;
import com.gym.owner.dbrepo.GymExpenseListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GymExpenseListService {


    @Autowired
    private GymExpenseListRepo gymExpenseListRepo;
    @Autowired
    private GymExpenseListQueryRepo gymExpenseListQueryRepo;

    public List<GymExpenseList> getGymExpenseList(int gym_id) {

       return gymExpenseListRepo.getExpenseList(gym_id);
    }

    public List<Map<String, Object>> getGymExpenseListQuery(int gym_id) {

        return  gymExpenseListQueryRepo.getGymExpenseListQueriesBy(gym_id);
    }
}
