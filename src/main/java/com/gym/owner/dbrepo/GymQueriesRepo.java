package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymQueries;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface GymQueriesRepo extends JpaRepository<GymQueries, Integer> {




    @Query("select qu.id as id,qu.phone as phone,qu.query as query,users.name as added,qu.createdon as addedOn from GymQueries qu,GymUsers users where qu.added=users.id and qu.gymid=:gymid and qu.status=true order by qu.id LIMIT 10 OFFSET :offset ")
    public List<Map<String, Object>> getQueries(@Param("gymid") int gymid, @Param("offset") int offset);



    @Modifying
    @Transactional
    @Query(
            value = "update gym_queries set status=false,updated=:user,updatedon=current_timestamp where id=:id ",
            nativeQuery = true
    )
    Integer  disableQueries(@Param("id") int id,@Param("user") int user);



}
