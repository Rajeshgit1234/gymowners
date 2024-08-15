package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GymUsersRepo extends JpaRepository<GymUsers, Integer> {

    @Query("SELECT u FROM GymUsers u WHERE u.username = :username and u.password = :password and u.active=true")
    public GymUsers loginGymUsers(@Param("username") String username, @Param("password") String password);

    public GymUsers findByUsernameAndActive(String username, boolean active);
    public Optional<GymUsers> findByActiveAndProfileAndGym(boolean active, int profile,int gym);

}
