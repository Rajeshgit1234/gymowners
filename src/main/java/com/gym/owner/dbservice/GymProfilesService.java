package com.gym.owner.dbservice;

import com.gym.owner.DB.GymProfiles;
import com.gym.owner.dbrepo.GymProfilesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymProfilesService {

    @Autowired
    private GymProfilesRepo gymProfilesRepo;

    public List<GymProfiles> findAllProfiles(){

        return  gymProfilesRepo.findByStatus(true);
    }
    public GymProfiles saveNewProfile(GymProfiles gymProfiles){

        return  gymProfilesRepo.save(gymProfiles);
    }
}
