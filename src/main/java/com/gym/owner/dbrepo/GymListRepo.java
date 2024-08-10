package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymList;
import org.springframework.data.repository.CrudRepository;

public interface GymListRepo  extends CrudRepository<GymList, Integer> {
}
