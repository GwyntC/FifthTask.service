package com.fifthtask.service.service.controller;

import com.fifthtask.service.service.dto.ProductSearchDto;
import com.fifthtask.service.service.dto.ProductUpdateDto;
import com.fifthtask.service.service.dto.RestResponse;
import com.fifthtask.service.service.model.Product;
import com.fifthtask.service.service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get/{id}")
    public Product getProduct(@PathVariable long id) {
        Product res=productService.getProduct(id);
        return res;
    }
    @GetMapping("/get")
    public List<Product> getAll(){
        return productService.getProducts();
    }

    @PostMapping("/create")
    public RestResponse createProduct(@RequestBody Product product) {
        long id = productService.createProduct(product);
        return new RestResponse(String.valueOf(id));
    }

    @PutMapping("/update/{id}")
    public RestResponse updateProduct(@PathVariable long id, @RequestBody ProductUpdateDto product) {
        long respCode = productService.updateProduct(id, product);
        return new RestResponse(String.valueOf(respCode));
    }

    @PostMapping("/_search")
    public List<Product> searchProduct(@RequestBody ProductSearchDto dto) {//add passing body
        return productService.search(dto.getBrandName(), dto.getCategory(),dto.getPageStart(),dto.getPagesCount());
    }

    @DeleteMapping("delete/{id}")
    public RestResponse deleteProduct(@PathVariable long id) {
        long respCode = productService.deleteProduct(id);
        return new RestResponse(String.valueOf(respCode));
    }
}
