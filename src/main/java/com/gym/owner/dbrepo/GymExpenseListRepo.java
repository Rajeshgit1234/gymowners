package com.gym.owner.dbrepo;

import com.gym.owner.DB.ExpenseMaster;
import com.gym.owner.DB.GymExpenseList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface GymExpenseListRepo extends JpaRepository<GymExpenseList, Integer> {



    //@Query("SELECT u FROM GymExpenseList u WHERE  u.status=true and u.gym_id = 1 order by  u.id ")
    @Query("SELECT u FROM GymExpenseList u WHERE  u.status=true and u.gym_id in (0,:gym) order by u.id ")
    //@Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true and u.gym_id in (0,:gym) order by u.id ")

    public List<GymExpenseList> getExpenseList(@Param("gym") int gym);

    @Query("SELECT sum(u.amount) as amount FROM GymExpenseList u WHERE  u.status=true and u.gym_id in (0,:gym) and u.created_on>:dateStr ")
    public List<Map<String, Object>>  getExpenseSumMonth(@Param("gym") int gym, @Param("dateStr") Timestamp dateStr);

    @Modifying
    @Transactional
    @Query(
            value = "update gym_expense_list set  created_on=:exp_date,exp_id=:expid,exp_remarks=:exp_remarks ,amount=:amount where id=:id ",
            nativeQuery = true
    )
    Integer  editExp(@Param("id") int id, @Param("expid") int expid, @Param("exp_date") Timestamp exp_date,@Param("exp_remarks") String exp_remarks, @Param("amount") float amount);


}
