package com.finalproject.secondhand.dto.response;

import com.finalproject.secondhand.entity.Offers;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OfferResponse {

    Integer offerId;
    String ImageProfil;
    String fullname;
    String city;
    String Image1;
    String Image2;
    String Image3;
    String Image4;
    String productName;
    String price;
    String priceNegotiated;
    String statusProcess;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public OfferResponse(Offers offers){
        this.offerId=offers.getOfferId();
        this.ImageProfil=offers.getUsers().getImageProfil();
        this.fullname=offers.getUsers().getFullname();
        this.city=offers.getUsers().getCity();
        this.Image1=offers.getProduct().getImage1();
        if (offers.getProduct().getImage2() == null) {
            this.Image2=offers.getProduct().getImage2();
        }
        if (offers.getProduct().getImage2() == null) {
            this.Image3=offers.getProduct().getImage3();
        }
        if (offers.getProduct().getImage2() == null) {
            this.Image4=offers.getProduct().getImage4();
        }
        this.productName=offers.getProduct().getProductName();
        this.price=offers.getProduct().getPrice();
        this.priceNegotiated=offers.getPriceNegotiated();
        this.statusProcess=offers.getStatusProcess().name();
        this.createdAt=offers.getCreatedAt();
        this.updatedAt=offers.getUpdatedAt();
    }

}
