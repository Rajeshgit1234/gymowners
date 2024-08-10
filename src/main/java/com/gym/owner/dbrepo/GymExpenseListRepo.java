package com.gym.owner.dbrepo;

import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymExpenseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GymExpenseListRepo extends JpaRepository<GymExpenseList, Integer> {



    //@Query("SELECT u FROM GymExpenseList u WHERE  u.status=true and u.gym_id = 1 order by  u.id ")
    @Query("SELECT u FROM GymExpenseList u WHERE  u.status=true and u.gym_id in (0,:gym) order by u.id ")
    //@Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true and u.gym_id in (0,:gym) order by u.id ")

    public List<GymExpenseList> getExpenseList(@Param("gym") int gym);

}