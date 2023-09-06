package com.finalproject.secondhand.dto.response;

import com.finalproject.secondhand.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationResponse {

    Integer id;
    Integer offerId;
    Integer productId;
    Integer userId;
    String image1;
    String image2;
    String image3;
    String image4;
    String title;
    String info;
    String productName;
    String price;
    String priceNegotiated;
    Boolean isRead;
    Boolean isSold;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public NotificationResponse(Notification notification){
        this.id=notification.getId();
        this.productId=notification.getProductId().getProductId();
        this.userId=notification.getUserId().getUserId();
        this.image1=notification.getProductId().getImage1();
        if (notification.getProductId().getImage2() != null){
            this.image2=notification.getProductId().getImage2();
        }
        if (notification.getProductId().getImage3() != null){
            this.image3=notification.getProductId().getImage3();
        }
        if (notification.getProductId().getImage4() != null){
            this.image4=notification.getProductId().getImage4();
        }
        this.title=notification.getTitle();
        this.info=notification.getInfo();
        this.productName=notification.getProductId().getProductName();
        this.price=notification.getProductId().getPrice();
        if (notification.getOfferId() != null) {
            this.offerId = notification.getOfferId().getOfferId();
            this.priceNegotiated=notification.getOfferId().getPriceNegotiated();
        }
        this.isRead=notification.getIsRead();
        this.isSold=notification.getProductId().getIsSold();
        this.createdAt=notification.getCreatedAt();
        this.updatedAt=notification.getUpdatedAt();
    }
}
