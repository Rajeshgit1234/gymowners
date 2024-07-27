package com.gym.owner.dbservice;

import com.gym.owner.DB.GymOwner;
import com.gym.owner.dbrepo.GymOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GymOwnerService {

    @Autowired
    private GymOwnerRepo gymOwnerRepo;

    public List<GymOwner> findAll(){

        return  gymOwnerRepo.findAll();
    }

}
