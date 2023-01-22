package com.fifthtask.service.service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductUpdateDto {
    private String modelName;
    private String brandName;
    private String country;
    private BigDecimal price;
    private long categoryId;
}
