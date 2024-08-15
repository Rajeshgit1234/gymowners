package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymUserPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GymUserPaymentsRepo  extends JpaRepository<GymUserPayments, Integer> {

    public Optional<GymUserPayments> findByCustomerAndGymAndStatus(int customer, int gym, boolean status);

    @Query(
            value = "SELECT *  FROM GymUserPayments where customer=:customer and gym=:gym and status=true order by list.createdon desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getCustomerPayments(@Param("gym") int gym, @Param("gym") int customer, @Param("limit") int limit, @Param("offset") int offset);

    @Query(
            value = "SELECT id, gym, customer, addedby, amount, createdon, description, status, subscription FROM gym_user_payments  where  gym=:gym and status=true order by createdon desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPayments(@Param("gym") int customer, @Param("limit") int limit, @Param("offset") int offset);


}
