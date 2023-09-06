package com.finalproject.secondhand.repository;

import com.finalproject.secondhand.entity.Notification;

import com.finalproject.secondhand.entity.Offers;
import com.finalproject.secondhand.entity.Products;
import com.finalproject.secondhand.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findNotificationByUserIdAndIsRead(Users users, Boolean status);

    Notification findNotificationById(Integer id);
    Notification findNotificationByOfferId(Offers offerId);
    Notification findNotificationByProductId(Products productId);

}