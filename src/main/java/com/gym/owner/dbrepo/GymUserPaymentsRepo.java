package com.gym.owner.dbrepo;

import com.gym.owner.DB.GymUserPayments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
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
            value = "SELECT payments.id, payments.gym, payments.customer, payments.addedby, payments.amount, payments.createdon, payments.description, payments.status,payments.paymonth,payments.payyear, subscription.description as subscription,users.name FROM gym_user_payments payments,gym_users users,gym_subscription_plans subscription  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and subscription.id=payments.subscription order by payments.createdon desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPayments(@Param("gym") int gym, @Param("limit") int limit, @Param("offset") int offset);
    @Query(
            value = "SELECT payments.id, payments.gym, payments.customer, payments.addedby, payments.amount, payments.createdon, payments.description, payments.status,payments.paymonth,payments.payyear, subscription,users.name FROM gym_user_payments payments,gym_users users  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.paymonth=:paymonth and payments.payyear=:payyear  order by payments.paymonth,payments.id desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPaymentsFilterMonthYear(@Param("gym") int gym, @Param("payyear") int payyear,@Param("paymonth") int paymonth,@Param("limit") int limit, @Param("offset") int offset);
    @Query(
            value = "SELECT sum(payments.amount) as amount FROM gym_user_payments payments,gym_users users  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.paymonth=:paymonth and payments.payyear=:payyear",
            nativeQuery = true
    )
    List<Map<String, Object>> getPaySumMonth(@Param("gym") int gym, @Param("payyear") int payyear,@Param("paymonth") int paymonth);


    @Query(
            value = "SELECT payments.id, payments.gym, payments.customer, payments.addedby, payments.amount, payments.createdon, payments.description, payments.status,payments.paymonth,payments.payyear, subscription,users.name FROM gym_user_payments payments,gym_users users  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.payyear=:payyear  order by payments.paymonth,payments.id desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPaymentsFilterYear(@Param("gym") int gym,@Param("payyear") int payyear, @Param("limit") int limit, @Param("offset") int offset);
    @Query(
            value = "SELECT payments.id, payments.gym, payments.customer, payments.addedby, payments.amount, payments.createdon, payments.description, payments.status,payments.paymonth,payments.payyear, subscription,users.name FROM gym_user_payments payments,gym_users users  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.payyear=:payyear and payments.customer=:customer order by payments.paymonth,payments.id desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPaymentsFilterCustomerYear(@Param("gym") int gym,@Param("payyear") int payyear, @Param("customer") int customer, @Param("limit") int limit, @Param("offset") int offset);



    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
            value = "update gym_user_payments set updatedby=:updatedby,updatedon=current_timestamp, status=false where id =:id ",
            nativeQuery = true
    )
    Integer  delPay( @Param("id") int id,@Param("updatedby") int updatedby);


}
