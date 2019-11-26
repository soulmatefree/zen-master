package com.soulmatefree.soulmate.model.vo;

import lombok.Data;

@Data
public class Dictionary {

    private Integer id;

    private String category;

    private String value;

    private String text;

    private Integer itemOrder;

    private Boolean isBulitIn;
}
