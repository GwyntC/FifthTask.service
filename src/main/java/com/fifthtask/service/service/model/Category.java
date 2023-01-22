package com.fifthtask.service.service.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Category {
    private long id;
    private String name;
}
