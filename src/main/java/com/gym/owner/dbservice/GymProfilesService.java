package com.gym.owner.dbservice;

import com.gym.owner.DB.GymList;
import com.gym.owner.DB.GymProfiles;
import com.gym.owner.dbrepo.GymProfilesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<GymProfiles> findById(Integer gymId){

        return  gymProfilesRepo.findById(gymId);
    }
    public GymProfiles checkProfileExist(String profile_name,Integer gymId){

        return  gymProfilesRepo.findGymProfilesByName(profile_name,1);
    }
}
