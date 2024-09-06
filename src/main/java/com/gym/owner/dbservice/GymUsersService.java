package com.gym.owner.dbservice;

import com.gym.owner.DB.GymUsers;
import com.gym.owner.controller.Common;
import com.gym.owner.dbrepo.GymUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GymUsersService {

    @Autowired
    private GymUsersRepo gymUsersRepo;

    public List<GymUsers> findAll(){

        return  gymUsersRepo.findAll();
    }
    public Optional<GymUsers> findByUser_id(Integer user_id){

        return  gymUsersRepo.findById(user_id);
    }
    public Optional<GymUsers> findCustomers(int gym_id,int profile_id){

        return gymUsersRepo.findByActiveAndProfileAndGym(true, profile_id,gym_id);
    }

    public GymUsers loginService(String username, String password){

        return  gymUsersRepo.loginGymUsers(username,password);
    }
    public GymUsers saveUser(GymUsers gymUsers) {

        return  gymUsersRepo.save(gymUsers);
    }
    public GymUsers findUserExist(GymUsers gymUsers) {

        return  gymUsersRepo.findByUsernameAndActive(gymUsers.getUsername(), gymUsers.getActive());
    }

}
