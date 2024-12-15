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
            value = "SELECT *  FROM gym_user_payments as gup where gup.id=:paymentid and gup.status=true;",
            nativeQuery = true
    )
    List<Map<String, Object>> findCustomerPaymentById(@Param("paymentid") int paymentid);

    @Query(
            value = "SELECT payments.id, payments.gym, payments.customer, payments.addedby, payments.amount, payments.createdon, payments.description, payments.status,payments.paymonth,payments.payyear, subscription.description as subscription,users.name,payments.frompaydate as frompaydate,payments.topaydate as topaydate,payments.finalamount as finalamount,payments.discountadded as discountadded FROM gym_user_payments payments,gym_users users,gym_subscription_plans subscription  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.payyear>=:year and subscription.id=payments.subscription order by payments.createdon desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPayments(@Param("gym") int gym, @Param("year") int year, @Param("limit") int limit, @Param("offset") int offset);
     @Query(
            value = "SELECT payments.id, payments.gym, payments.customer, payments.addedby, payments.amount, payments.createdon, payments.description, payments.status,payments.paymonth,payments.payyear, subscription.description as subscription,users.name,payments.fromdoy as fromdoy,payments.todoy as todoy,subscription.duration,payments.frompaydate as frompaydate,payments.topaydate as topaydate,payments.finalamount as finalamount,payments.discountadded as discountadded FROM gym_user_payments payments,gym_users users,gym_subscription_plans subscription  where users.id=:customer and users.id=payments.customer and  payments.status=true and subscription.id=payments.subscription order by payments.createdon desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPaymentsCustomer( @Param("customer") int customer, @Param("limit") int limit, @Param("offset") int offset);


     @Query(
            value = "SELECT payments.id, payments.gym, payments.customer, payments.addedby, payments.amount, payments.createdon, payments.description, payments.status,payments.paymonth,payments.payyear, users.subscription,users.name,payments.fromdoy as fromdoy,payments.todoy as todoy ,payments.frompaydate as frompaydate,payments.topaydate as topaydate ,payments.finalamount as finalamount,payments.discountadded as discountadded FROM gym_user_payments payments,gym_users users  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.topaydate>=:queryDate order by payments.paymonth,payments.id desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPaymentsFilterMonthYear(@Param("gym") int gym,@Param("queryDate")  java.sql.Date queryDate, @Param("limit") int limit, @Param("offset") int offset);

    @Query(
            value = "SELECT sum(payments.amount) as amount,CAST(payments.createdon AS DATE)  as createdon FROM gym_user_payments payments,gym_users users  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.topaydate>=:qDate group by  createdon",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPaymentsMonth(@Param("gym") int gym, @Param("qDate") java.sql.Date qDate);


    @Query(
            value = "SELECT sum(payments.amount) as amount FROM gym_user_payments payments,gym_users users  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.topaydate>=:qDate",
            nativeQuery = true
    )
    List<Map<String, Object>> getPaySumMonth(@Param("gym") int gym, @Param("qDate") java.sql.Date qDate);


    @Query(
            value = "SELECT payments.id, payments.gym, payments.customer, payments.addedby, payments.amount, payments.createdon, payments.description, payments.status,payments.paymonth,payments.payyear, subscription,users.name FROM gym_user_payments payments,gym_users users  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.payyear=:payyear  order by payments.paymonth,payments.id desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPaymentsFilterYear(@Param("gym") int gym,@Param("payyear") int payyear, @Param("limit") int limit, @Param("offset") int offset);

    @Query(
            value = "select gu.id as id,gu.name as name from gym_users gu where gu.gym=:gym and gu.profile=:profile and gu.id not in (select gup.customer from gym_user_payments gup  where gup.topaydate>=current_date) order by id desc;",
            nativeQuery = true
    )
    List<Map<String, Object>> getpendingPaymentsList(@Param("gym") int gym,@Param("profile") int profile);


    @Query(
            value = "SELECT payments.id, payments.gym, payments.customer, payments.addedby, payments.amount, payments.createdon, payments.description, payments.status,payments.paymonth,payments.payyear, users.subscription,users.name,payments.fromdoy as fromdoy,payments.todoy as todoy,payments.frompaydate as frompaydate,payments.topaydate as topaydate,payments.finalamount as finalamount,payments.discountadded as discountadded FROM gym_user_payments payments,gym_users users  where users.id=payments.customer and  payments.gym=:gym and payments.status=true and payments.topaydate>=:queryDate  and payments.customer=:customer order by payments.paymonth,payments.id desc LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<Map<String, Object>> getGymPaymentsFilterCustomerYear(@Param("gym") int gym, @Param("queryDate")  java.sql.Date queryDate, @Param("customer") int customer, @Param("limit") int limit, @Param("offset") int offset);



    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
            value = "update gym_user_payments set updatedby=:updatedby,updatedon=current_timestamp, status=false where id =:id ",
            nativeQuery = true
    )
    Integer  delPay( @Param("id") int id,@Param("updatedby") int updatedby);


}
