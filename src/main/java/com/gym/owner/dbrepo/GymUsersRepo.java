package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymUsers;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GymUsersRepo extends JpaRepository<GymUsers, Integer> {

    @Query("SELECT u FROM GymUsers u WHERE u.phone = :phone and u.password = :password and u.active=true and u.weblog=true")
    public GymUsers loginGymUsers(@Param("phone") String phone, @Param("password") String password);

    @Modifying
    @Transactional
    @Query("update GymUsers u set u.password=:password WHERE u.id = :userid and u.active=true and u.weblog=true")
    public int updatePassword(@Param("userid") int userid, @Param("password") String password);

    public GymUsers findByUsernameAndActive(String username, boolean active);
    public GymUsers findByPhoneAndActive(String phone, boolean active);

    @Query("SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.profile = :profile and u.active=true and u.addedby=users.id  order by u.id desc LIMIT 10 OFFSET :offset")
    public List<Map<String, Object>>  findByActiveAndProfileAndGym( @Param("profile")int profile, @Param("gym")int gym, @Param("offset")int offset);

    @Query("SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.phone = :phone and u.active=true and u.addedby=users.id  order by u.id desc LIMIT 10 OFFSET :offset")
    public List<Map<String, Object>>  findCustomersWithPhone(  @Param("gym")int gym, @Param("offset")int offset, @Param("phone")String phone);

    @Query("SELECT u  FROM GymUsers  u WHERE u.uniquetoken=:token and u.active=true and u.weblog =false ")
    public GymUsers  verifyToken(  @Param("token")String token);

     @Query("SELECT u  FROM GymUsers  u WHERE u.uniquetoken=:token  and u.phone=:phone and u.active=true and u.weblog =false ")
    public GymUsers  verifyTokenAndPhone(  @Param("token")String token,@Param("phone")String phone);


    @Modifying
    @Transactional
    @Query(
            value = "update gym_users  set   password=:password,weblog=true  where phone=:phone ",
            nativeQuery = true
    )
    Integer  regToken(@Param("phone") String phone, @Param("password") String password);


    @Modifying
    @Transactional
    @Query(
            value = "update gym_users  set   recentactivity=recentactivity+1   where id=:userid and active=true ",
            nativeQuery = true
    )
    Integer  updateRecentActivity(@Param("userid") int userid);


}
