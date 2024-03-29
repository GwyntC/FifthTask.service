package com.fifthtask.service.service.service;

import com.fifthtask.service.service.dto.ProductUpdateDto;
import com.fifthtask.service.service.model.Category;
import com.fifthtask.service.service.model.Product;

import java.util.List;

public interface ProductService {
    long createProduct(Product product);

    Product getProduct(long id);

    List<Product> getProducts();

    long updateProduct(long id, ProductUpdateDto product);

    int deleteProduct(long id);

    List<Product> search(String brandName, String category,int startPage,int pagesCount);

    List<Category> getAllCategories();
}
