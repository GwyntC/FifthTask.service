package com.fifthtask.service.service.controller;

import com.fifthtask.service.service.model.Category;
import com.fifthtask.service.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ProductService productService;

    @GetMapping("getAll")
    public List<Category> getCategories() {
        return productService.getAllCategories();
    }
}
