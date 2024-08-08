package com.gym.owner.dbrepo;

import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseMasterRepo extends JpaRepository<ExpenseMaster, Integer> {



    @Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true order by u.id")
    public List<ExpenseMaster> getActiveExpenseList();


}
