package com.fifthtask.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fifthtask.service.service.dto.RestResponse;
import com.fifthtask.service.service.model.Product;
import com.fifthtask.service.service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ServiceApplication.class)
@AutoConfigureMockMvc
public class ProductControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addProductTest() throws Exception {
        String modelName = "HP Pavilion Aero 13-be0027ua (5A5Z1EA) Silver";
        String brandName = "Rudenko";
        String country = "Kazakhstan";
        double price = 180.09;
        int categoryId = 1;
        String body = """
                  {
                      "modelName":"%s",
                      "brandName":"%s",
                      "country":"%s",
                      "price":%.2f,
                      "categoryId":%d
                  }               
                """.formatted(modelName, brandName, country, price, categoryId);
        MvcResult mvcResult = mvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andReturn();
        RestResponse restResponse = parseResponse(mvcResult, RestResponse.class);
        int productId = Integer.parseInt(restResponse.getResult());
        assertThat(productId).isGreaterThanOrEqualTo(1);

    }

    @Test
    public void getProductTest() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/products/getproduct/29")
                )
                .andReturn();
        Product product = parseResponse(mvcResult, Product.class);
        assertThat(product.getId()).isEqualTo(29);
        assertThat(product.getBrandName()).isEqualTo("Rudenko");
    }

    @Test
    public void updateProductTest() throws Exception {
        String modelName = "HP Pavilion Aero 13-be0027ua (5A5Z1EA) Silver";
        String brandName = "HP Pavilion";
        String country = "Estonia";
        double price = 180.06;
        int categoryId = 2;
        String body = """
                  {
                      "modelName":"%s",
                      "brandName":"%s",
                      "country":"%s",
                      "price":%.2f,
                      "categoryId":%d
                  }               
                """.formatted(modelName, brandName, country, price, categoryId);
        MvcResult mvcResult = mvc.perform(put("/api/products/updateproduct/29")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andReturn();
        RestResponse response = parseResponse(mvcResult, RestResponse.class);
        assertThat(response.getResult()).isEqualTo("200");
    }

    @Test
    public void searchProducts() throws Exception {
        String brandName = "ACER";
        String categoryName = "laptops";
        String body = """
                  {
                      "brandName":"%s",
                      "category":"%s"
                  }               
                """.formatted(brandName, categoryName);
        MvcResult mvcResult = mvc.perform(post("/api/products/_search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andReturn();
        List<Product> productList = parseResponse(mvcResult, List.class);
        assertThat(productList.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void deleteProduct() throws Exception {
        MvcResult mvcResult = mvc.perform(delete("/api/products/delete/28")
                )
                .andReturn();
        RestResponse res = parseResponse(mvcResult, RestResponse.class);
        assertThat(res.getResult()).isEqualTo("200");
    }

    private <T> T parseResponse(MvcResult mvcResult, Class<T> c) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), c);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json", e);
        }
    }

}
