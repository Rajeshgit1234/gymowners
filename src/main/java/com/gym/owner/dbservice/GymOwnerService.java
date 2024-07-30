package com.gym.owner.dbservice;

import com.gym.owner.DB.GymOwner;
import com.gym.owner.dbrepo.GymOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GymOwnerService {

    @Autowired
    private GymOwnerRepo gymOwnerRepo;

    public List<GymOwner> findAll(){

        return  gymOwnerRepo.findAll();
    }
    public Optional<GymOwner> findByUser_id(Integer user_id){

        return  gymOwnerRepo.findById(user_id);
    }

    public GymOwner loginService(String username,String password){

        return  gymOwnerRepo.loginGymOwners(username,password);
    }

}
