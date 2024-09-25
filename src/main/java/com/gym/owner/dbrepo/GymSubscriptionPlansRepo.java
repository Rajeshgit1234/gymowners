package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymExpenseList;
import com.gym.owner.DB.GymProfiles;
import com.gym.owner.DB.GymSubscriptionPlans;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface GymSubscriptionPlansRepo  extends JpaRepository<GymSubscriptionPlans, Integer> {


    @Query("SELECT u.id as subId,u.description as subName,u.added as addedOn,users.name as addedBy  FROM GymSubscriptionPlans u,GymUsers users WHERE  u.status=true and u.gym in (0,:gym) and u.added=users.id order by u.id LIMIT 10 OFFSET :offset")

    public List<Map<String, Object>> getPlans(@Param("gym") int gym, @Param("offset") int offset);

    @Query("SELECT u  FROM GymSubscriptionPlans u WHERE  u.status=true and u.gym in (0,:gym) order by u.id ")

    public List<GymSubscriptionPlans> getPlansFull(@Param("gym") int gym);


    @Query("SELECT u  FROM GymSubscriptionPlans u WHERE  u.status=true and u.gym in (:gym) and u.id=:subId ")

    public List<GymSubscriptionPlans> getPlansFullById(@Param("gym") int gym,@Param("subId") int subId);


    @Query("SELECT u FROM GymSubscriptionPlans u WHERE  u.status=true and u.gym in (0,:gym) and UPPER( u.description)=:description order by u.id ")

    public GymSubscriptionPlans checkIfExist(@Param("gym") int gym,@Param("description") String description);


    @Modifying
    @Transactional
    @Query(
            value = "update gym_subscription_plans set status=false where id=:id ",
            nativeQuery = true
    )
    Integer  delSubscription(@Param("id") int id);



}
