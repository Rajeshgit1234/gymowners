package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GymProfilesRepo extends JpaRepository<GymProfiles, Integer> {

    public List<GymProfiles> findByStatus(Boolean status);
    @Query("SELECT u.id,u.gym_id,u.created_at,u.profile_name,u.status,u.updated_at as amount FROM GymProfiles u WHERE  u.status=true and u.profile_name= :name and u.gym_id in (0,:gym_id)  ")

    public GymProfiles findGymProfilesByName(String name, Integer gym_id);
}
