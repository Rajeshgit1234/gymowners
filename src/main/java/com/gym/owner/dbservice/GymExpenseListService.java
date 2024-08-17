package com.gym.owner.dbservice;

import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymExpenseList;
import com.gym.owner.DB.GymExpenseListQuery;
import com.gym.owner.dbrepo.GymExpenseListQueryRepo;
import com.gym.owner.dbrepo.GymExpenseListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    public List<Map<String, Object>> getGymExpenseListQuery(int gym_id,int limit,int offset) {

        return  gymExpenseListQueryRepo.getGymExpenseListQueriesBy(gym_id,limit,offset);
    }
    public List<Map<String, Object>> getGymExpenseListQueryFilterDateAndType(int gym_id,int type,Timestamp str_date,Timestamp end_date,int limit,int offset) {

        return  gymExpenseListQueryRepo.getGymExpenseListQueryFilterDateAndType(gym_id,type,str_date,end_date,limit,offset);
    }
    public List<Map<String, Object>> getGymExpenseListQueryFilterType(int gym_id,int type,int limit,int offset) {

        return  gymExpenseListQueryRepo.getGymExpenseListQueryFilterType(gym_id,type,limit,offset);
    }public List<Map<String, Object>> getGymExpenseListQueryFilterDate(int gym_id,Timestamp str_date,Timestamp end_date,int limit,int offset) {

        return  gymExpenseListQueryRepo.getGymExpenseListQueryFilterDate(gym_id,str_date,end_date,limit,offset);
    }
    public GymExpenseList saveExpenses(GymExpenseList gymExpenseList) {

        return  gymExpenseListRepo.save(gymExpenseList);
    }
    public List<Map<String, Object>>  getExpenseSumMonth(int gym_id, Timestamp str_date) {

        return  gymExpenseListRepo.getExpenseSumMonth(gym_id,str_date);
    }
}
