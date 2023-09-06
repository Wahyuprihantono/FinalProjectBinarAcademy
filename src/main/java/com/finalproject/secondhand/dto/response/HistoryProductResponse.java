package com.finalproject.secondhand.dto.response;

import com.finalproject.secondhand.entity.Products;
import lombok.Data;

@Data
public class HistoryProductResponse {

    Integer productId;
    String image1;
    String image2;
    String image3;
    String image4;
    String productName;
    Integer categoryId;
    String categoryName;
    String price;
    String description;
    String imageProfil;
    String fullname;
    String city;
    boolean isSold;
    boolean isWishlist;

    public HistoryProductResponse(Products products) {
        this.productId=products.getProductId();
        this.image1=products.getImage1();
        if (products.getImage2() == null) {
            this.image2=products.getImage2();
        }
        if (products.getImage3() == null) {
            this.image3=products.getImage3();
        }
        if (products.getImage4() == null) {
            this.image4=products.getImage4();
        }
        this.productName=products.getProductName();
        this.categoryId=products.getCategories().getId();
        this.categoryName=products.getCategories().getName().name();
        this.price=products.getPrice();
        this.description=products.getDescription();
        this.imageProfil=products.getUsers().getImageProfil();
        this.fullname=products.getUsers().getFullname();
        this.city=products.getUsers().getCity();
        this.isSold=products.getIsSold();
        this.isWishlist=products.getIsWishlist();
    }

}
