package com.fifthtask.service.service.service;

import com.fifthtask.service.service.dto.ProductUpdateDto;
import com.fifthtask.service.service.exceptions.NotFoundException;
import com.fifthtask.service.service.model.Category;
import com.fifthtask.service.service.model.Product;
import com.fifthtask.service.service.repository.CategoryRepository;
import com.fifthtask.service.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public long createProduct(Product product) {
        return productRepository.createProduct(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(long id) {
        idCheck(id);
        Product product=getOrThrow(id);
        return product;
    }

    @Override
    public long updateProduct(long id, ProductUpdateDto dto) {
        idCheck(id);
        Product product=getOrThrow(id);
        return productRepository.updateProduct(id, dto);
    }

    @Override
    @Transactional
    public int deleteProduct(long id) {
        idCheck(id);
        Product product=getOrThrow(id);
        return productRepository.deleteProduct(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> search(String brandName, String category) {
        if(brandName==null || category==null){
            throw new IllegalArgumentException("Brand or category can not be null");
        } else if (brandName=="" || category=="") {
            throw new IllegalArgumentException("Brand or category can not be empty");
        }
        else if(brandName==" " || category==" ") {
                throw new IllegalArgumentException("Brand or category can not be spaces");
        }
        List<Product> productList=searchOrThrow(brandName, category);
        return productList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.getAll();
    }

    private Product getOrThrow(long id){
        return  productRepository.getProductById(id)
                .orElseThrow(()->new NotFoundException("Product with id %d not found".formatted(id)));
    }
    private List<Product>searchOrThrow(String brandName, String category){
        return productRepository.searchProduct(brandName,category).orElseThrow(()->
                new NotFoundException("Product with such brand %s and category %s not found".formatted(brandName,category)));
    }
    private void idCheck(long id){
        if(id<=0){
            throw new IllegalArgumentException("Id has to be greater than zero ");
        }
    }
}
