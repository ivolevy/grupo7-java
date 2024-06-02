package com.example.uade.tpo.repository;

import com.example.uade.tpo.entity.ProductsCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductCategoriesRepository extends JpaRepository<ProductsCategories, Long> {
    List<Long> getProductsIdByCategoryId(Long categoryId);
}

