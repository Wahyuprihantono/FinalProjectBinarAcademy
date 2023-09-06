package com.finalproject.secondhand.controller;

import com.finalproject.secondhand.entity.Offers;
import com.finalproject.secondhand.entity.Products;
import com.finalproject.secondhand.entity.Users;
import com.finalproject.secondhand.enums.EStatusProcess;
import com.finalproject.secondhand.dto.response.OfferResponse;
import com.finalproject.secondhand.service.product.ProductService;
import com.finalproject.secondhand.service.transaction.OfferService;
import com.finalproject.secondhand.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@Tag(name = "Offer", description = "API for processing Transaction")
@RequestMapping("/api/offer/")
@SecurityRequirement(name = "Authorization")
@CrossOrigin(origins = {"http://localhost:3000", "*"}, allowedHeaders = "*")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "Api whatsapp by status accepted")
    @GetMapping("show-offer/whastapp/{offerId}")
    public ResponseEntity<?> apiWhatsapp(@PathVariable Integer offerId) {
        return new ResponseEntity<>(offerService.whatsappSender(offerId), HttpStatus.OK);
    }

    @Operation(summary = "Show offer by Buyer")
    @GetMapping("show-offer/{offerId}")
    public ResponseEntity<OfferResponse> showOffer(@PathVariable Integer offerId) {
        Offers offers = offerService.findByOfferId(offerId);
        OfferResponse response = new OfferResponse(offers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Add offers")
    @PostMapping("add/{productId}")
    public ResponseEntity<?> saveOffer(
            @Schema (example = "{\n" + "  \"price\": \"250000\"\n" + "}")
            @RequestBody Map<String, Object> priceNegotiated,
            @PathVariable (name = "productId") Integer productId, Authentication valid) {
        Products products = productService.findProductById(productId);
        String username = valid.getName();
        Users users = userService.findByUsername(username);
        if (products.getIsWishlist().equals(false)){
            products.setIsWishlist(true);
            Offers offers = new Offers();
            offers.setUsers(users);
            offers.setProduct(products);
            offers.setPriceNegotiated(priceNegotiated.get("price").toString());
            offers.setStatusProcess(offers.getStatusProcess());
            offerService.saveOffer(offers);
            return new ResponseEntity<>("Offer Added", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("product has been offer", HttpStatus.OK);
        }
    }

    @Operation(summary = "Update status offers accepted or rejected")
    @PutMapping("update/{offerId}/{status}")
    public ResponseEntity<?> updateStatusProcess(@PathVariable ("offerId") Integer offerId,
                                                  @PathVariable ("status") String status) {
        Offers offers = offerService.findByOfferId(offerId);
        Products products = productService.findProductById(offers.getProduct().getProductId());
        if (offers.getStatusProcess().equals(EStatusProcess.WAITING)) {
            if (Objects.equals(status, "accepted")) {
                offers.setStatusProcess(EStatusProcess.ACCEPTED);
                products.setIsWishlist(true);
                offerService.updateStatusOffer(products, offers, offerId);
                return new ResponseEntity<>("Status Accepted", HttpStatus.ACCEPTED);
            } else if (Objects.equals(status, "rejected")) {
                offers.setStatusProcess(EStatusProcess.REJECTED);
                products.setIsWishlist(false);
                offerService.updateStatusOffer(products, offers, offerId);
                return new ResponseEntity<>("Status Rejected", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Status not updated", HttpStatus.FORBIDDEN);
            }
        } else  {
            return new ResponseEntity<>("Product not yet bid", HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Update status offers sold or notSold")
    @PutMapping("update/isSold/{offerId}/{status}")
    public ResponseEntity<?> updateStatusSold(@PathVariable ("offerId") Integer offerId,
                                              @PathVariable ("status") String status) {
        Offers offers = offerService.findByOfferId(offerId);
        Products products = productService.findProductById(offers.getProduct().getProductId());
        if (offers.getStatusProcess().equals(EStatusProcess.ACCEPTED)) {
            if (Objects.equals(status, "notSold")) {
                products.setIsSold(false);
                products.setIsWishlist(false);
                offerService.updateStatusOffer(products, offers, offerId);
                return new ResponseEntity<>("Product status updated successfully", HttpStatus.ACCEPTED);
            } else if (Objects.equals(status, "sold")) {
                products.setIsSold(true);
                products.setIsWishlist(false);
                offerService.updateStatusOffer(products, offers, offerId);
                return new ResponseEntity<>("Product status updated successfully", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Product status not updated", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("Product not accepted", HttpStatus.FORBIDDEN);
        }
    }
}