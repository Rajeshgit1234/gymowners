package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymWebAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GymWebAccessRepo extends JpaRepository<GymWebAccess, Integer> {


    @Query(
            value = "SELECT u from gym_web_acess u where u.gymid=:gym and profile=:profile",
            nativeQuery = true
    )
    List<Map<String, Object>> findByGymidAndProfile(@Param("gym") int gym, @Param("profile") int profile);


}
