package com.finalproject.secondhand.service.transaction;

import com.finalproject.secondhand.entity.Offers;
import com.finalproject.secondhand.entity.Products;
import com.finalproject.secondhand.entity.Users;
import com.finalproject.secondhand.enums.EStatusProcess;
import com.finalproject.secondhand.repository.OfferRepository;
import com.finalproject.secondhand.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService{

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<Offers> findWishlistUser(Users users) {
        return offerRepository.findOffersByProduct_UsersAndProduct_IsWishlistAndProduct_IsSold(users, true, false);
    }

    @Override
    public Offers findByOfferId(Integer offerId) {
        return offerRepository.findByOfferId(offerId);
    }

    @Override
    public String whatsappSender(Integer offerId) {
        Offers offers = offerRepository.findByOfferId(offerId);
        if (offers.getStatusProcess() == EStatusProcess.ACCEPTED) {
            String numberPhone = offers.getUsers().getPhone();
            String apiWhatsapp = "https://api.whatsapp.com/send?phone=";
            return apiWhatsapp+numberPhone;
        }else {
         return "Product not list in status offer accepted";
        }
    }

    @Override
    public void saveOffer(Offers body) {
        Offers offers = new Offers();
        offers.setUsers(body.getUsers());
        offers.setProduct(body.getProduct());
        offers.setPriceNegotiated(body.getPriceNegotiated());
        offers.setStatusProcess(body.getStatusProcess());
        offerRepository.save(offers);
        notificationService.saveNotificationOffer("Penawaran produk", "Ada yang menawar barang anda", offers, offers.getProduct(), offers.getProduct().getUsers(), false);
    }

    @Override
    public void updateStatusOffer(Products products, Offers offers, Integer offerId) {
        productRepository.save(products);
        offerRepository.save(offers);
        if (offers.getStatusProcess().equals(EStatusProcess.ACCEPTED)) {
            notificationService.saveNotificationOffer("Penawaran produk diterima", "penawaran anda telah diterima", offers, offers.getProduct(), offers.getUsers(), false );
        } else if (offers.getStatusProcess().equals(EStatusProcess.REJECTED)) {
            notificationService.saveNotificationOffer("Penawaran produk ditolak", "penawaran anda telah ditolak", offers, offers.getProduct(), offers.getUsers(), false );
        } else if (offers.getStatusProcess().equals(EStatusProcess.ACCEPTED) && products.getIsSold().equals(true)) {
            notificationService.saveNotificationOffer("Penawaran produk", "produk berhasil terjual", offers, offers.getProduct(), offers.getProduct().getUsers(), false );
        }
    }
}
