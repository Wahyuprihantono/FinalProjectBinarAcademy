package com.finalproject.secondhand.controller;

import com.finalproject.secondhand.entity.Categories;
import com.finalproject.secondhand.entity.Products;
import com.finalproject.secondhand.dto.response.ProductResponse;
import com.finalproject.secondhand.service.product.CategoriesService;
import com.finalproject.secondhand.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Homepage", description = "API for access homepage")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/homepage/")
@CrossOrigin(origins = {"http://localhost:3000", "*"}, allowedHeaders = "*")
public class HomepageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoriesService categoriesService;

    @Operation(summary = "Find product by productId no Authentication")
    @GetMapping("{productId}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable ("productId") Integer productId) {
        return new ResponseEntity<>(productService.findByProductId(productId), HttpStatus.OK);
    }

    @Operation(summary = "NOT FOR PUBLIC")
    @GetMapping("show-products")
    public ResponseEntity<List<ProductResponse>> allProduct(){
        List<Products> product = productService.showAllProduct();
        List<ProductResponse> productResponse =
                product.stream().map(ProductResponse::new).collect(
                        Collectors.toList());
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @Operation(summary = "List Categories")
    @GetMapping("list-category")
    public ResponseEntity<List<Categories>> listCategory() {
        return new ResponseEntity<>(categoriesService.findAllCategories(), HttpStatus.OK);
    }

    @Operation(summary = "Show homepage all products sort and filter (validasi user login)")
    @GetMapping("get-product")
    public ResponseEntity<Map<String, Object>> getAllProductPage(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Integer category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            Authentication authentication
    ) {
        try {
            String username = authentication.getName();
            Pageable paging = PageRequest.of(page - 1, size, Sort.by("price"));
            Page<Products> productPage = productService.getHomepageLogin(productName, category, paging, username);
            List<Products> products = productPage.getContent();
            Map<String, Object> response = new HashMap<>();
            List<ProductResponse> productResponse =
                    products.stream().map(ProductResponse::new).collect(
                            Collectors.toList());
            response.put("products", productResponse);
            response.put("currentPage", productPage.getNumber() + 1);
            response.put("totalProducts", productPage.getTotalElements());
            response.put("totalPages", productPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NullPointerException e) {
            Pageable paging = PageRequest.of(page - 1, size, Sort.by("price"));
            Page<Products> productPage = productService.getHomepage(productName, category, paging);
            List<Products> products = productPage.getContent();
            Map<String, Object> response = new HashMap<>();
            List<ProductResponse> productResponse =
                    products.stream().map(ProductResponse::new).collect(
                            Collectors.toList());
            response.put("products", productResponse);
            response.put("currentPage", productPage.getNumber() + 1);
            response.put("totalProducts", productPage.getTotalElements());
            response.put("totalPages", productPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
    }
}
