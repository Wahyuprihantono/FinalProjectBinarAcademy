package com.finalproject.secondhand.dto.response;

import com.finalproject.secondhand.entity.Products;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class ProductResponse {

    Integer productId;
    String image1;
    String image2;
    String image3;
    String image4;
    String productName;
    String category;
    String price;
    String description;
    String imageProfil;
    String username;
    String fullname;
    String city;
    boolean isPublished;
    boolean isSold;
    boolean isWishlist;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public ProductResponse(Products products) {
        this.productId=products.getProductId();
        this.image1=products.getImage1();
        if (products.getImage2() != null) {
            this.image2=products.getImage2();
        }
        if (products.getImage3() != null){
            this.image3=products.getImage3();
        }
        if (products.getImage4() != null){
            this.image4=products.getImage4();
        }
        this.productName=products.getProductName();
        this.category=products.getCategories().getName().name();
        this.price=products.getPrice();
        this.description=products.getDescription();
        this.imageProfil=products.getUsers().getImageProfil();
        this.username=products.getUsers().getUsername();
        this.fullname=products.getUsers().getFullname();
        this.city=products.getUsers().getCity();
        this.isPublished=products.getIsPublished();
        this.isSold=products.getIsSold();
        this.isWishlist=products.getIsWishlist();
        this.createdAt=products.getCreatedAt();
        this.updatedAt=products.getUpdatedAt();
    }
}
