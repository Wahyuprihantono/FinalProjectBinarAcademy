package com.finalproject.secondhand.service.product;

import com.finalproject.secondhand.config.utils.HasLogger;
import com.finalproject.secondhand.entity.Categories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoriesService extends HasLogger {

    Categories loadCategoryByCategoryId(Integer categoryId);

    List<Categories> findAllCategories();

}

