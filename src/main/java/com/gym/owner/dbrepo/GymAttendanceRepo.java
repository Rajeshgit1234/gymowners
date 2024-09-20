package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface GymAttendanceRepo extends JpaRepository<GymAttendance, Integer> {




    @Query("SELECT u.id as attid,users.name as user,u.doy as doy,u.year as year,u.fromhour as fromhour,u.tohour as tohour,u.createdon as createdon FROM GymAttendance u,GymUsers  users WHERE  u.status=true and u.gymid =:gymId and u.doy=:doy and u.userid=users.id order by u.id  LIMIT 10  OFFSET :offset")
    public List<Map<String, Object>> findAllByGymidAndStatus(@Param("gymId") int gymId, @Param("doy") int doy, @Param("offset") int offset);

    @Query("SELECT u.id as attid,users.name as user,u.doy as doy,u.year as year,u.fromhour as fromhour,u.tohour as tohour,u.createdon as createdon FROM GymAttendance u,GymUsers  users WHERE  u.status=true and u.gymid =:gymId and u.doy=:doy and u.userid=:userid and u.userid=users.id order by u.id  LIMIT 10  OFFSET :offset")
    public List<Map<String, Object>> findAllByGymidAndUseridStatus(@Param("gymId") int gymId, @Param("userid") int userid,@Param("doy") int doy, @Param("offset") int offset);

    @Query("select users.id as userid,users.name as name,att.doy as doy from GymUsers users left join GymAttendance att  on users.id=att.userid and   att.doy between :doy and :doyend  where users.active=true and  users.id=:userid and users.gym =:gymId and users.profile=:profile  group by users.id,users.name,att.doy order by att.doy LIMIT 10  OFFSET :offset ")
    public List<Map<String, Object>> findAllByGymidBetweenDOYUserId(@Param("gymId") int gymId,@Param("userid") int userid, @Param("profile") int profile, @Param("doy") int doy,@Param("doyend") int doyend, @Param("offset") int offset);

    @Query("select users.id as userid,users.name as name,att.doy as doy from GymUsers users left join GymAttendance att  on users.id=att.userid and   att.doy between :doy and :doyend  where users.active=true and users.gym =:gymId and users.profile=:profile  group by users.id,users.name,att.doy order by att.doy LIMIT 10  OFFSET :offset ")
    public List<Map<String, Object>> findAllByGymidBetweenDOY(@Param("gymId") int gymId,@Param("profile") int profile,@Param("doy") int doy, @Param("doyend") int doyend, @Param("offset") int offset);


    @Query("select users.id as id from GymUsers users  where users.active=true and   users.gym =:gymId and users.profile=:profile   order by users.id LIMIT 20  OFFSET :offset  ")
    public List<Map<String, Object>> fetchCustomers(@Param("gymId") int gymId,@Param("profile") int profile,@Param("offset") int offset);



    @Query("select  lttable.id as userid,lttable.name as name,att.doy as doy,att.datedoy as datedoy from  (SELECT u.id as id,u.name as name FROM GymUsers u WHERE u.gym=:gymId and u.profile=:profile and u.active=true  order by u.recentactivity desc LIMIT 10  OFFSET :offset) as lttable left  join GymAttendance  att on lttable.id=att.userid and att.doy between :doy and :doyend ")
    public List<Map<String, Object>> fetchGymAttendance(@Param("gymId") int gymId,@Param("profile") int profile,@Param("doy") int doy, @Param("doyend") int doyend, @Param("offset") int offset);




}
