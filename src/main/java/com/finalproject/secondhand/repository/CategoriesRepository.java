package com.finalproject.secondhand.repository;

import com.finalproject.secondhand.entity.Categories;
import com.finalproject.secondhand.enums.CategoryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

    Optional<Categories> findByName(CategoryList categoryList);
    Optional<Categories> findById(Integer categoryId);

}