package com.gym.owner.dbservice;

import com.gym.owner.DB.GymExpenseList;
import com.gym.owner.DB.GymList;
import com.gym.owner.DB.GymOwners;
import com.gym.owner.DB.GymUsers;
import com.gym.owner.dbrepo.GymOwnersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GymOwnersService {


    @Autowired
    private GymOwnersRepo gymOwnersRepo;



    public GymOwners saveOwner(GymOwners owner) {

        return  gymOwnersRepo.save(owner);
    }

    public List<GymOwners> getAllOwners(int gymid) {

        return gymOwnersRepo.findAllByGymidAndActive(gymid);
    }
    public List<GymOwners> getAllOwnerGymDetails(int owner) {

        return gymOwnersRepo.getAllOwnerGymDetails(owner);
    }
    public GymOwners findOwnerExist(GymOwners gymowner) {

        return  gymOwnersRepo.findOwnerExist(gymowner.getOwner(),gymowner.getGymid());
    }
}
