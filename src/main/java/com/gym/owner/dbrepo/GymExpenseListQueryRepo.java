package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymExpenseList;
import com.gym.owner.DB.GymExpenseListQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface GymExpenseListQueryRepo extends JpaRepository<GymExpenseListQuery, Integer> {

    @Query("SELECT list.id,owner.name,list.created_on,list.amount ,list.exp_id,master.expense_item,list.exp_remarks FROM GymExpenseList list,GymOwner owner,ExpenseMaster master WHERE  list.status=true and list.gym_id in (1)  and list.exp_id=master.id and list.created_by=master.id order by list.id ")
    //@Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true and u.gym_id in (0,:gym) order by u.id ")

    public List<GymExpenseListQuery> getExpenseList();
    //public List<GymExpenseListQuery> getExpenseList(@Param("gym") int gym);

    @Query(
            value = "SELECT list.id,owner.name,list.created_on,list.amount ,list.exp_id,master.expense_item,list.exp_remarks FROM gym_expense_list list,gym_owners  owner,expense_master  master WHERE  list.status=true and list.gym_id in (:gym)  and list.exp_id=master.id and list.created_by=master.id order by list.id  ",
            nativeQuery = true
    )
    List<Map<String, Object>>  getGymExpenseListQueriesBy(@Param("gym") int gym);



}