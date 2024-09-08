package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymExpenseList;
import com.gym.owner.DB.GymOwners;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface GymOwnersRepo extends CrudRepository<GymOwners, Integer> {


    @Query("SELECT u FROM GymOwners u WHERE  u.active=true and u.gymid =:gym order by u.id ")
    //@Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true and u.gym_id in (0,:gym) order by u.id ")

    public List<GymOwners>  findAllByGymidAndActive(@Param("gym") int gym);

    @Query("SELECT u FROM GymOwners u WHERE  u.active=true and u.owner =:owner order by u.id ")
    //@Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true and u.gym_id in (0,:gym) order by u.id ")

    public List<GymOwners>  getAllOwnerGymDetails(@Param("owner") int owner);

    @Query("SELECT u FROM GymOwners u WHERE  u.active=true and u.gymid =:gym and u.owner=:owner order by u.id ")
    //@Query("SELECT u FROM ExpenseMaster u WHERE  u.status=true and u.gym_id in (0,:gym) order by u.id ")

    public GymOwners  findOwnerExist(@Param("owner") int owner,@Param("gym") int gym);


}
