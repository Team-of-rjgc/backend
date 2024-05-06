package com.gdut.lostfound.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class CategoryKey implements Serializable {
    private String name;
    private Integer level;
    private String targetId;
}
