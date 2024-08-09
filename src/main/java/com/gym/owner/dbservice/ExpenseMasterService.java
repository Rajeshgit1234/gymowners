package com.gym.owner.dbservice;

import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.dbrepo.ExpenseMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseMasterService {

    @Autowired
    private ExpenseMasterRepo expenseMasterRepo;

    public List<ExpenseMaster> findAll(){

        return  expenseMasterRepo.findAll();
    }

    public List<ExpenseMaster> findActiveExpenseMaster(int gym){

        return expenseMasterRepo.getActiveExpenseList(gym);
    }
}
