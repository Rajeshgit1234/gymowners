package com.gym.owner.dbservice;

import com.gym.owner.DB.GymAttendance;
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

    public List<Map<String, Object>>  findAllByGymId(int gymId,int doy,int offset) {
        return gymAttendanceRepo.findAllByGymidAndStatus(gymId,doy,offset);
    }
    public List<Map<String, Object>>  findAllByGymidAndUseridStatus(int gymId,int userid,int doy,int offset) {
        return gymAttendanceRepo.findAllByGymidAndUseridStatus(gymId,userid,doy,offset);
    }
}
