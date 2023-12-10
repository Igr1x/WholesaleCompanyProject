package ru.varnavskii.wholesalecompany.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopGoodsEntity {
    private String name;
    private Integer goodCount;

    public TopGoodsEntity(String name, Integer goodCount) {
        this.name = name;
        this.goodCount = goodCount;
    }
}
