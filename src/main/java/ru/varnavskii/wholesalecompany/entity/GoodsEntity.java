package ru.varnavskii.wholesalecompany.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsEntity {
    private Integer id;
    private String name;
    private Integer priority;

    public GoodsEntity(Integer id, String name, Integer priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "GoodsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
