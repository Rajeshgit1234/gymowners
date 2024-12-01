package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymDietPlans;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface GymDietPlansRepo extends JpaRepository<GymDietPlans, Integer> {


    @Query("SELECT u.id FROM GymDietPlans u WHERE  u.status=true and UPPER(u.diet_details) = :dietplan and u.gymid in (0,:gymId)")
    public List<GymDietPlans> checkDietExist(@Param("dietplan") String dietplan, @Param("gymId") int gymId);

    @Query("SELECT u.id as id,u.diet_details as details,u.diet_name as dietname,u.created as created,users.name as added FROM GymDietPlans u,GymUsers users WHERE u.addedby=users.id and u.status=true and u.gymid in (0,:gymid) order by u.id LIMIT 10 OFFSET :offset")
    public List<Map<String, Object>> findByGymidAndStatus(@Param("gymid") int gymid,@Param("offset") int offset);

    @Query("SELECT u.id as id,u.diet_details as details,u.diet_name as dietname,u.created as created,users.name as added FROM GymDietPlans u,GymUsers users WHERE u.addedby=users.id and u.status=true and u.gymid in (0,:gymid) order by u.id ")
    public List<Map<String, Object>> findByGymidAndStatusFull(@Param("gymid") int gymid);

    @Modifying
    @Transactional
    @Query(
            value = "update gym_diet_plans set  updated=current_timestamp ,status=false,updatedby=:userid where id=:id ",
            nativeQuery = true
    )
    Integer  delDietPlan(@Param("id") int id, @Param("userid") int userid);

}

