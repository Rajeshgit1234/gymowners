package com.gym.owner.dbservice;

import com.gym.owner.DB.GymList;
import com.gym.owner.dbrepo.GymListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GymListService {

    @Autowired
    private GymListRepo gymListRepo;

    public Optional<GymList> findById(Integer gymId){

        return  gymListRepo.findById(gymId);
    } public List<GymList> findByActive(){

        return  gymListRepo.findByActive(true);
    }

    public GymList saveGym(GymList gymList) {

        return  gymListRepo.save(gymList);
    }

}
