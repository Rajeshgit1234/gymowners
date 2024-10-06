package com.gym.owner.dbservice;

import com.gym.owner.DB.GymProfiles;
import com.gym.owner.DB.GymQueries;
import com.gym.owner.dbrepo.GymQueriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public class GymQueriesService {
    @Autowired
    private GymQueriesRepo gymQueriesRepo;


    public GymQueries save(GymQueries query){

        return  gymQueriesRepo.save(query);
    }

    public List<Map<String, Object>> getQueries(int gymid,int offset){

        return  gymQueriesRepo.getQueries(gymid,offset);
    }
    public int disableQueries(int id,int user){

        return  gymQueriesRepo.disableQueries(id,user);
    }

}
