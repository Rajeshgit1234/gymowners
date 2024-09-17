package com.gym.owner.dbrepo;

import com.gym.owner.DB.ExpenseMaster;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ExpenseMasterRepo extends JpaRepository<ExpenseMaster, Integer> {



    @Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true and u.gym_id in (0,:gym) order by u.id ")
    public List<ExpenseMaster> getActiveExpenseList(@Param("gym") int gym);


    @Query("SELECT u.id as expId,u.expense_item as expItem,users.name as addedby,u.addedon as added FROM ExpenseMaster u,GymUsers  users WHERE  u.status=true and u.gym_id in (0,:gym)  and u.added=users.id order by u.id  LIMIT 10  OFFSET :offset")
    public List<Map<String, Object>> findActiveExpenseMasterBasesGYM(@Param("gym") int gym,@Param("offset") int offset);


    @Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true and u.gym_id in (0,:gym) and upper(u.expense_item)=:expName order by u.id ")
    public ExpenseMaster checkIfExist(@Param("gym") int gym,@Param("expName") String expName);

    @Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true and u.gym_id=:gym and u.id=:expName order by u.id ")
    public ExpenseMaster checkIfExistItemId(@Param("gym") int gym,@Param("expName") int expName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update ExpenseMaster set status=false,updatedby=:userid,updateon=current_timestamp  WHERE  id=:expItem and gym_id=:gym"
            )
    public int updateExpMasterItem(@Param("gym") int gym,@Param("userid") int userid,@Param("expItem") int expItem);


}
