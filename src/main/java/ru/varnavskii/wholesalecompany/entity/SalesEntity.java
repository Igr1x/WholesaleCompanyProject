package ru.varnavskii.wholesalecompany.entity;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class SalesEntity {
    private Integer id;
    private Integer goodCount;
    private Timestamp createDate;
    private Integer goodId;

    public SalesEntity(Integer id, Integer goodCount, Timestamp createDate, Integer goodId) {
        this.id = id;
        this.goodCount = goodCount;
        this.createDate = createDate;
        this.goodId = goodId;
    }

    @Override
    public String toString() {
        return "SalesEntity{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", goodCount=" + goodCount +
                ", good_id=" + goodId +
                '}';
    }
}
