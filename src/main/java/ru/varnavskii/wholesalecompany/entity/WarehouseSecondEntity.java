package ru.varnavskii.wholesalecompany.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseSecondEntity {
    private Integer id;
    private Integer goodId;
    private Integer goodCount;

    public WarehouseSecondEntity(Integer id, Integer goodId, Integer goodCount) {
        this.id = id;
        this.goodId = goodId;
        this.goodCount = goodCount;
    }

    @Override
    public String toString() {
        return "WarehouseSecondEntity{" +
                "id=" + id +
                ", goodCount=" + goodCount +
                ", goodId=" + goodId +
                '}';
    }
}
