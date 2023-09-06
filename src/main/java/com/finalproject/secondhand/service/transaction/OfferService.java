package com.finalproject.secondhand.service.transaction;

import com.finalproject.secondhand.entity.Offers;

import com.finalproject.secondhand.entity.Products;
import com.finalproject.secondhand.entity.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OfferService {

    List<Offers> findWishlistUser(Users users);
    Offers findByOfferId(Integer offerId);
    String whatsappSender(Integer offerId);
    void saveOffer(Offers offers);
    void updateStatusOffer(Products products, Offers body, Integer offerId);

}
