package com.gym.owner.dbservice;

import com.gym.owner.DB.GymAttendance;
import com.gym.owner.controller.Common;
import com.gym.owner.dbrepo.GymAttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GymAttendanceService {

    @Autowired
    private GymAttendanceRepo gymAttendanceRepo;

    public GymAttendance save(GymAttendance gymAttendance) {
        return gymAttendanceRepo.save(gymAttendance);
    }

    /*public GymAttendance checkExist(GymAttendance gymAttendance) {
        return gymAttendanceRepo.checkExist(gymAttendance);
    }*/

    public List<Map<String, Object>>  findAllByGymId(int gymId,int doy,int offset) {
        return gymAttendanceRepo.findAllByGymidAndStatus(gymId,doy,offset);
    }
    public List<Map<String, Object>>  findAllByGymidAndUseridStatus(int gymId,int userid,int doy,int offset) {
        return gymAttendanceRepo.findAllByGymidAndUseridStatus(gymId,userid,doy,offset);
    }

    public List<Map<String, Object>>  findAllByGymidBetweenDOYUserId(int gymId,int userid,int doy,int doyend,int offset) {
        return gymAttendanceRepo.findAllByGymidBetweenDOYUserId(gymId,userid, Common.GYM_CUSTOMERS,doy,doyend,offset);
    }

    public List<Map<String, Object>>  findAllByGymidBetweenDOY(int gymId,int doy,int doyend,int offset) {
        return gymAttendanceRepo.findAllByGymidBetweenDOY(gymId, Common.GYM_CUSTOMERS,doy,doyend,offset);
    }

    public List<Map<String, Object>>  fetchCustomers(int gymId,int offset) {
        return gymAttendanceRepo.fetchCustomers(gymId, Common.GYM_CUSTOMERS,offset);
    }
    public List<Map<String, Object>>  fetchGymAttendance(int gymId,int year,int doy,int doyend,int offset) {
        return gymAttendanceRepo.fetchGymAttendance(gymId, Common.GYM_CUSTOMERS,doy,doyend,offset);
    }public List<Map<String, Object>>  fetchGymCustomerAttendance(int gymId,int customer,int year,int doy,int doyend) {
        return gymAttendanceRepo.fetchGymCustomerAttendance(gymId, customer,Common.GYM_CUSTOMERS,doy,doyend);
    }
}
