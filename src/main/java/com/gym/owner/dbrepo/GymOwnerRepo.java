package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymOwnerRepo extends JpaRepository<GymOwner, Integer> {
}
