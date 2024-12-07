package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GymListRepo  extends CrudRepository<GymList, Integer> {
    public List<GymList> findById(int id);
    public List<GymList> findByActive(Boolean active);
}
