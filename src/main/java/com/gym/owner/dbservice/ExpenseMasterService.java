package com.gym.owner.dbservice;

import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.dbrepo.ExpenseMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpenseMasterService {

    @Autowired
    private ExpenseMasterRepo expenseMasterRepo;

    public List<ExpenseMaster> findAll(){

        return  expenseMasterRepo.findAll();
    }
    public ExpenseMaster saveExpenseMaster(ExpenseMaster expenseMaster){

        return  expenseMasterRepo.save(expenseMaster);
    }

    public List<ExpenseMaster> findActiveExpenseMaster(int gym){

        return expenseMasterRepo.getActiveExpenseList(gym);
    }
    public List<Map<String, Object>> findActiveExpenseMasterBasesGYM(int gym,int offset){

        return expenseMasterRepo.findActiveExpenseMasterBasesGYM(gym,offset);
    }
    public int updateExpMasterItem(int gym,int userid,int expItem){

        return expenseMasterRepo.updateExpMasterItem(gym,userid,expItem);
    }
    public ExpenseMaster checkIfExist(int gym,String expName){

        return expenseMasterRepo.checkIfExist(gym,expName);
    } public ExpenseMaster checkIfExistItemId(int gym,int expName){

        return expenseMasterRepo.checkIfExistItemId(gym,expName);
    }
}
