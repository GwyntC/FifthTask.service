package com.fifthtask.service.service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductSearchDto {
    private String brandName;
    private String category;
}
