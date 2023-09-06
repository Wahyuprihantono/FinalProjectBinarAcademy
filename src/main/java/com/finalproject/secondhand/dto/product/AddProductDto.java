package com.finalproject.secondhand.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDto {

    private String productName;
    private Integer categoryId;
    private String price;
    private String description;

}
