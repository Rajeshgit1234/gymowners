package com.gym.owner.dbservice;

import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymExpenseListQuery;
import com.gym.owner.dbrepo.ExpenseMasterRepo;
import com.gym.owner.dbrepo.GymExpenseListQueryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymExpenseListQueryService {

    @Autowired
    private GymExpenseListQueryRepo gymExpenseListQueryRepo;

    public List<GymExpenseListQuery> getExpenseList(int gym){

        List<GymExpenseListQuery> result = gymExpenseListQueryRepo.getExpenseList();
        return result;
    }

}
