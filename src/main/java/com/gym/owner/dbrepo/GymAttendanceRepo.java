package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface GymAttendanceRepo extends JpaRepository<GymAttendance, Integer> {




    @Query("SELECT u.id as attid,users.name as addedby,u.doy as doy,u.year as year,u.fromhour as fromhour,u.tohour as tohour,u.createdon as createdon FROM GymAttendance u,GymUsers  users WHERE  u.status=true and u.gymid =:gymId and u.doy=:doy and u.userid=users.id order by u.id  LIMIT 10  OFFSET :offset")
    public List<Map<String, Object>> findAllByGymidAndStatus(@Param("gymId") int gymId, @Param("doy") int doy, @Param("offset") int offset);

    @Query("SELECT u.id as attid,users.name as addedby,u.doy as doy,u.year as year,u.fromhour as fromhour,u.tohour as tohour,u.createdon as createdon FROM GymAttendance u,GymUsers  users WHERE  u.status=true and u.gymid =:gymId and u.doy=:doy and u.userid=:userid and u.userid=users.id order by u.id  LIMIT 10  OFFSET :offset")
    public List<Map<String, Object>> findAllByGymidAndUseridStatus(@Param("gymId") int gymId, @Param("userid") int userid,@Param("doy") int doy, @Param("offset") int offset);


}
