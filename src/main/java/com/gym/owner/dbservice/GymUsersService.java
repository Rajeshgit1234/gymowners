package com.gym.owner.dbservice;

import com.gym.owner.DB.GymUsers;
import com.gym.owner.controller.Common;
import com.gym.owner.dbrepo.GymUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    public List<Map<String, Object>> findCustomers(int gym_id, int profile_id,int offset){

        return gymUsersRepo.findByActiveAndProfileAndGym( profile_id,gym_id,offset);
    }
    public List<Map<String, Object>> findCustomersWithPhone(int gym_id,int offset,String phone){

        return gymUsersRepo.findCustomersWithPhone( gym_id,offset,phone);
    }

    public GymUsers loginService(String phone, String password){

        return  gymUsersRepo.loginGymUsers(phone,password);
    }
    public GymUsers saveUser(GymUsers gymUsers) {

        return  gymUsersRepo.save(gymUsers);
    }
    public GymUsers findUserExist(GymUsers gymUsers) {

        return  gymUsersRepo.findByPhoneAndActive(gymUsers.getPhone(), gymUsers.getActive());
    }

}
