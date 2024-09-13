package com.gym.owner.dbservice;

import com.gym.owner.DB.GymList;
import com.gym.owner.DB.GymWebAccess;
import com.gym.owner.dbrepo.GymWebAccessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GymWebAccessService {

    @Autowired
    private GymWebAccessRepo gymWebAccessRepo;

    public List<Map<String, Object>> getGymWebAccessByProfile(int gym, int profile) {

        return gymWebAccessRepo.findByGymidAndProfile( gym, profile);
    }

    public GymWebAccess saveProfile(GymWebAccess gymWebAccess) {

        return  gymWebAccessRepo.save(gymWebAccess);
    }
}
