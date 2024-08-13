package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymProfiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymProfilesRepo extends JpaRepository<GymProfiles, Integer> {

    public List<GymProfiles> findByStatus(Boolean status);
}
