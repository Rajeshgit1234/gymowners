package com.gym.owner.dbservice;

import com.gym.owner.DB.GymList;
import com.gym.owner.DB.GymOwner;
import com.gym.owner.dbrepo.GymListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GymListService {

    @Autowired
    private GymListRepo gymListRepo;

    public Optional<GymList> findById(Integer gymId){

        return  gymListRepo.findById(gymId);
    }


}
