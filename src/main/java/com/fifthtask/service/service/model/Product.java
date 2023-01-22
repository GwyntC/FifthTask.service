package com.fifthtask.service.service.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
public class Product {
    private long id;

    @NotBlank(message = "model name is required")
    private String modelName;

    @NotBlank(message = "brand name is required")
    private String brandName;

    @NotBlank(message = "country name is required")
    private String country;
    private BigDecimal price;

    @NotNull(message = "categoryId is required")
    private long categoryId;
}
