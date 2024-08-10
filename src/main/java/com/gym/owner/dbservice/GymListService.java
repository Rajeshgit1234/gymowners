package com.gym.owner.dbservice;

import com.gym.owner.dbrepo.GymListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GymListService {

    @Autowired
    private GymListRepo gymListRepo;


}
