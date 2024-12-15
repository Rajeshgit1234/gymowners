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

    @Modifying
    @Transactional
    @Query("update GymUsers u set u.pt=:ptuser,u.created=current_timestamp,u.updatedby=:userid WHERE u.id = :customer and u.active=true and u.gym=:gym_id")
    public int mapPT(@Param("gym_id") int gym_id,@Param("customer") int customer, @Param("ptuser") int ptuser, @Param("userid") int userid);

    @Modifying
    @Transactional
    @Query("update GymUsers u set u.subscription=:sub,u.updated=current_timestamp,u.updatedby=:userid WHERE u.id = :customer and u.active=true and u.gym=:gym_id")
    public int mapSub(@Param("gym_id") int gym_id,@Param("customer") int customer, @Param("sub") int sub, @Param("userid") int userid);

    @Modifying
    @Transactional
    @Query("update GymUsers u set u.dietplan=:diet,u.diet=true,u.updated=current_timestamp,u.updatedby=:userid WHERE u.id = :customer and u.active=true and u.gym=:gym_id")
    public int mapDiet(@Param("gym_id") int gym_id,@Param("customer") int customer, @Param("diet") int diet, @Param("userid") int userid);

    public GymUsers findByUsernameAndActive(String username, boolean active);
    public GymUsers findByPhoneAndActive(String phone, boolean active);

   // @Query("SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.profile = :profile and u.active=true and u.addedby=users.id  order by u.id desc LIMIT 10 OFFSET :offset")
    //@Query("select  usertble.id as id,usertble.name as name,usertble.username as username,usertble.phone as phone,usertble.created as created,usertble.name as added ,usertble.address as address,gsp.description as description from (SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address,u.subscription as subscription FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.profile = :profile and u.active=true and u.addedby=users.id  order by u.id desc LIMIT 10 OFFSET :offset) as usertble left join GymSubscriptionPlans gsp  on gsp.id=usertble.subscription")
    @Query("select  usertble.id as id,usertble.name as name,usertble.username as username,usertble.phone as phone,usertble.created as created,usertble.name as added ,usertble.address as address,usertble.email as email,usertble.profile as profile,gsp.description as description,gdp.diet_name as dietname,usertble.dietplan as dietplan,usertble.subscription as subscription from (SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address,u.subscription as subscription,u.dietplan as dietplan,u.profile as profile,u.email as email FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.profile = :profile and u.active=true and u.addedby=users.id  order by u.id desc LIMIT 10 OFFSET :offset) as usertble left join GymSubscriptionPlans gsp  on gsp.id=usertble.subscription left join  GymDietPlans  gdp on gdp.id=usertble.dietplan")
    public List<Map<String, Object>>  findByActiveAndProfileAndGym( @Param("profile")int profile, @Param("gym")int gym, @Param("offset")int offset);

   // @Query("SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.profile = :profile and u.active=true and u.addedby=users.id  order by u.id desc LIMIT 10 OFFSET :offset")
    @Query("SELECT u FROM GymUsers u  WHERE u.profile=:profile and u.gym=:gym  and u.diet=true and u.dietplan=0 and u.created<current_date - 4 day " )
    public List<GymUsers>  findCustomerYetToAddDietPlan(  @Param("gym")int gym ,@Param("profile")int profile);

    @Query("SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address,u.subscription as subscription FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.profile = :profile and u.active=true and u.addedby=users.id  order by u.id desc ")
    public List<Map<String, Object>>  findByActiveAndProfileAndGymFull( @Param("profile")int profile, @Param("gym")int gym);

    @Query("SELECT u.id as id,u.profile as profile, u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.profile in ( :profile1,:profile2 ) and u.active=true and u.addedby=users.id  order by u.id desc ")
    public List<Map<String, Object>>  findByActiveAndProfilePTAndGymFull( @Param("profile1")int profile1,@Param("profile2")int profile2, @Param("gym")int gym);

    //@Query("SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.phone = :phone and u.active=true and u.addedby=users.id  order by u.id desc LIMIT 10 OFFSET :offset")
    @Query("select  usertble.id as id,usertble.name as name,usertble.username as username,usertble.phone as phone,usertble.created as created,usertble.name as added ,usertble.address as address,usertble.profile as profile,usertble.email as email , gsp.description as description,gdp.diet_name as dietname,usertble.dietplan as dietplan,usertble.subscription as subscription from (SELECT u.id as id,u.name as name,u.username as username,u.phone as phone,u.created as created,users.name as added ,u.address as address,u.subscription as subscription,u.dietplan as dietplan,u.profile as profile,u.email as email FROM GymUsers  u,GymUsers  users WHERE u.gym in( :gym ) and u.phone = :phone  and u.active=true and u.addedby=users.id  order by u.id desc LIMIT 50 OFFSET :offset) as usertble left join GymSubscriptionPlans gsp  on gsp.id=usertble.subscription left join GymDietPlans gdp on gdp.id=usertble.dietplan ")
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
