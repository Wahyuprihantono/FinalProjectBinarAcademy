package com.finalproject.secondhand.service.product;

import com.finalproject.secondhand.entity.Categories;
import com.finalproject.secondhand.enums.CategoryList;
import com.finalproject.secondhand.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoryRepository;

    @Bean
    public void addNewCategory() {

        for (CategoryList categoryList : CategoryList.values()) {
            try {
                Categories category = categoryRepository.findByName(categoryList)
                        .orElseThrow(() -> new NullPointerException(
                                String.format("Category: %s not found", categoryList)));
                getLogger().info("{} is found", category);
            } catch (RuntimeException e) {
                getLogger().info(String.format("Category: %s not found." + " It will be create ...", categoryList.name()));

                Categories category = new Categories();
                category.setName(categoryList);
                categoryRepository.save(category);
            }
        }
    }

    @Override
    public Categories loadCategoryByCategoryId(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NullPointerException("No category with id " + categoryId));
    }

    @Override
    public List<Categories> findAllCategories() {
        return categoryRepository.findAll();
    }

}
