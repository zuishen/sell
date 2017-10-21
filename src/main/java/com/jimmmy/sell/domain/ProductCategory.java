package com.jimmmy.sell.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    @Id
    @GeneratedValue
    /* 类目id */
    private Integer categoryId;

    /* 类目名字 */
    private String categoryName;

    /* 类目编号 */
    private Integer categoryType;

    /* 创建时间 */
    private Date createTime;

    /* 更新时间 */
    private Date updateTime;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductCategory() {
    }
}
