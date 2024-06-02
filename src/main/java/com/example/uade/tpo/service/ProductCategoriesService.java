package com.example.uade.tpo.service;

import com.example.uade.tpo.entity.ProductsCategories;
import com.example.uade.tpo.repository.IProductCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoriesService {

    @Autowired
    private IProductCategoriesRepository productCategoryRepository;

    public List<Long> getProductsIdByCategoryId(Long categoryId) {
        return productCategoryRepository.getProductsIdByCategoryId(categoryId);
    }

    public void createProductCategory(Long productId, Long categoryId) {
        ProductsCategories productCategory = new ProductsCategories();
        productCategory.setProductId(productId);
        productCategory.setCategoryId(categoryId);
        productCategoryRepository.save(productCategory);
    }

}
