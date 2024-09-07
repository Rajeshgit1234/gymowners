package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GymUsersRepo extends JpaRepository<GymUsers, Integer> {

    @Query("SELECT u FROM GymUsers u WHERE u.phone = :phone and u.password = :password and u.active=true")
    public GymUsers loginGymUsers(@Param("phone") String phone, @Param("password") String password);

    public GymUsers findByUsernameAndActive(String username, boolean active);
    public GymUsers findByPhoneAndActive(String phone, boolean active);

    @Query("SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address FROM GymUsers  u,GymUsers  users WHERE u.gym = :gym and u.profile = :profile and u.active=true and u.addedby=users.id ")
    public List<Map<String, Object>>  findByActiveAndProfileAndGym( @Param("profile")int profile, @Param("gym")int gym);

}
