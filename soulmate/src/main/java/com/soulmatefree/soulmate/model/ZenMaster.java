package com.soulmatefree.soulmate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZenMaster {

    private Integer id;

    private String name;

    private String description;

    private String nickName;

    private Boolean sex;

    private String dynasty;

    private Integer age;

    private String creator;

    private Date createTime;

    private String updator;

    private Date updateTime;

}