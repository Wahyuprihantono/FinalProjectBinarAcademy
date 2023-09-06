package com.finalproject.secondhand.service.product;

import com.finalproject.secondhand.entity.Products;
import com.finalproject.secondhand.entity.Users;
import com.finalproject.secondhand.repository.ProductRepository;
import com.finalproject.secondhand.repository.UserRepository;
import com.finalproject.secondhand.dto.response.ProductResponse;
import com.finalproject.secondhand.service.transaction.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Products> showAllProduct() {
        return productRepository.findAll();
    }

    @Autowired
    private NotificationService notificationService;

    @Override
    public Page<Products> getHomepageLogin(String productName, Integer category, Pageable pageable, String username) {
        Users users = userRepository.findUsersByUsername(username);
        Integer userId = users.getUserId();
        if (productName == null && category == null){
            return productRepository.findAllLogin(userId, pageable);
        } else if (productName == null) {
            return productRepository.findByCategoryLogin(userId, category, pageable);
        } else if (category == null) {
            return productRepository.findByProductNameLogin(userId, productName, pageable);
        } else {
            return productRepository.findByProductNameContainingIgnoreCaseAndCategoryIgnoreCaseLogin(userId, productName, category, pageable);
        }
    }

    @Override
    public Page<Products> getHomepage(String productName, Integer category, Pageable pageable) {
        if (productName == null && category == null){
            return productRepository.findAll(pageable);
        } else if (productName == null) {
            return productRepository.findByCategory(category, pageable);
        } else if (category == null) {
            return productRepository.findByProductName(productName, pageable);
        } else {
            return productRepository.findByProductNameContainingIgnoreCaseAndCategoryIgnoreCase(productName, category, pageable);
        }
    }

    @Override
    public Boolean validasiProfil(String username) {
        Users validasi = userRepository.findByUsername(username);
        if (validasi.getFullname() != null) {
            if (validasi.getCity() != null) {
                if (validasi.getAddress() != null) {
                    return validasi.getPhone() != null;
                }
            }
        }
        return false;
    }


    @Override
    public Products findProductById(Integer productId) {
        return productRepository.getById(productId);
    }

    @Override
    public ProductResponse findByProductId(Integer productId) {
        Products products = productRepository.getById(productId);
        return new ProductResponse(products);
    }

    @Override
    public List<Products> findProductByUser(String username) {
        Users users = userRepository.findByUsername(username);
        Boolean isSold = false;
        return productRepository.findByUsersAndIsSold(users, isSold);
    }

    @Override
    public List<Products> findProductByUserByIsSold(String username) {
        Users users = userRepository.findByUsername(username);
        Boolean isSold = true;
        return productRepository.findByUsersAndIsSold(users, isSold);
    }

    @Override
    public ProductResponse save(Products body) {
        Products products = new Products();
        products.setProductName(body.getProductName());
        products.setCategories(body.getCategories());
        products.setPrice(body.getPrice());
        products.setDescription(body.getDescription());
        products.setImage1(body.getImage1());
        products.setImage2(body.getImage2());
        products.setImage3(body.getImage3());
        products.setImage4(body.getImage4());
        products.setUsers(body.getUsers());
        products.setIsPublished(body.getIsPublished());
        products.setIsSold(body.getIsSold());
        productRepository.save(products);
        if (products.getIsPublished().equals(true)) {
            notificationService.saveNotificationProduct("Produk berhasil diterbitkan", products, products.getUsers());
            return new ProductResponse(products);
        } else {
            return new ProductResponse(products);
        }
    }

    @Override
    public void publish(Products body, Integer productId) {
        Products products = productRepository.getById(productId);
        products.setIsPublished(body.getIsPublished());
        productRepository.save(products);
        if (products.getIsPublished().equals(true)) {
            notificationService.saveNotificationProduct("Produk berhasil diterbitkan", products, products.getUsers());
        }
    }

    @Override
    public void update(Products body, Integer productId) {
        Products products = productRepository.findProductsByProductId(productId);
        if (body.getProductName() != null) {
            if (body.getProductName().length() > 0) {
                products.setProductName(body.getProductName());
            }
        }
        if (body.getPrice() != null) {
            if (body.getPrice().length() > 0) {
                products.setPrice(body.getPrice());
            }
            products.setPrice(body.getPrice());
        }
        if (body.getDescription() != null) {
            if (body.getDescription().length() > 0) {
                products.setDescription(body.getDescription());
            }
        }
        if (body.getImage1() != null) {
            products.setImage1(body.getImage1());

        }
        if (body.getImage2()  != null) {
            products.setImage2(body.getImage2());

        }
        if (body.getImage3()  != null) {
            products.setImage3(body.getImage3());

        }
        if (body.getImage4()  != null) {
            products.setImage4(body.getImage4());
        }
        productRepository.save(products);
    }

    @Override
    public String deleteProduct(Integer productId) {
        productRepository.deleteProductsByProductId(productId);
        return "Product deleted";
    }
}

