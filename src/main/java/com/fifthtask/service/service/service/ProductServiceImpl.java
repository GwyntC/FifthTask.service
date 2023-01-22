package com.fifthtask.service.service.service;

import com.fifthtask.service.service.dto.ProductUpdateDto;
import com.fifthtask.service.service.model.Category;
import com.fifthtask.service.service.model.Product;
import com.fifthtask.service.service.repository.CategoryRepository;
import com.fifthtask.service.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public long createProduct(Product product) {
        return productRepository.createProduct(product);
    }

    @Override
    public Product getProduct(long id) {
        return productRepository.getProductById(id);
    }

    @Override
    public long updateProduct(long id, ProductUpdateDto dto) {
        return productRepository.updateProduct(id, dto);
    }

    @Override
    public int deleteProduct(long id) {
        return productRepository.deleteProduct(id);
    }

    @Override
    public List<Product> search(String brandName, String category) {
        return productRepository.searchProduct(brandName, category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.getAll();

    }
}
