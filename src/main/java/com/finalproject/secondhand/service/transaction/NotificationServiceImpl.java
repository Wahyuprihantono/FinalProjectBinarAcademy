package com.finalproject.secondhand.service.transaction;

import com.finalproject.secondhand.entity.Notification;
import com.finalproject.secondhand.entity.Offers;
import com.finalproject.secondhand.entity.Products;
import com.finalproject.secondhand.entity.Users;
import com.finalproject.secondhand.repository.NotificationRepository;
import com.finalproject.secondhand.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void saveNotificationOffer(String title, String info, Offers offersId, Products productsId, Users users,
                                 Boolean isRead) {
        Users user = userService.findByUsername(users.getUsername());
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setInfo(info);
        notification.setProductId(productsId);
        notification.setUserId(user);
        notification.setOfferId(offersId);
        notificationRepository.save(notification);
    }

    @Override
    public void saveNotificationProduct(String title, Products productsId, Users users) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setProductId(productsId);
        notification.setUserId(users);
        notificationRepository.save(notification);
    }

    @Override
    public void updateIsRead(Integer id) {
        Notification notification = notificationRepository.findNotificationById(id);
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findNotificationByUserId(Users users) {
        return notificationRepository.findNotificationByUserIdAndIsRead(users, false);
    }

}
