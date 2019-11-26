package com.soulmatefree.soulmate.model.ext;

import java.io.Serializable;

/**
 * @Author: baishuzi
 * @Date: 2019/3/18
 * @Description:com.soulmatefree.soulmate.model.ext
 * @version: 1.0
 */
public class ZenMasterQueue implements Serializable {

    private static final long serialVersionUID = 8556308032319161396L;

    private Integer id;

    private String name;

    private String nickName;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
