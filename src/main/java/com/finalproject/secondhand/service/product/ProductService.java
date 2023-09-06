package com.finalproject.secondhand.service.product;

import com.finalproject.secondhand.entity.Products;
import com.finalproject.secondhand.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {

    //Homepage
    List<Products> showAllProduct();
    Page<Products> getHomepageLogin(String productName, Integer category, Pageable pageable, String username);
    Page<Products> getHomepage(String productName, Integer category, Pageable pageable);

    //Validasi klik tombol jual
    Boolean validasiProfil(String username);

    Products findProductById(Integer productId);
    ProductResponse findByProductId(Integer productId);

    //History
    List<Products> findProductByUser(String username);
    List<Products> findProductByUserByIsSold(String username);

    // C R U D
    ProductResponse save(Products body);
    void publish(Products body, Integer productId);
    void update(Products body, Integer productId);
    String deleteProduct(Integer productId);

}