package com.jimmmy.sell.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jimmmy.sell.enums.PayStatusEnum;
import com.jimmmy.sell.enums.ProductStatusEnum;
import com.jimmmy.sell.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    /* 名字 */
    private String productName;

    /* 单价 */
    private BigDecimal productPrice;

    /* 库存 */
    private Integer productStock;

    /* 描述 */
    private String productDescription;

    /* 小图 */
    private String productIcon;

    /* 状态，0正常，1下架 */
    private Integer productStatus;

    /* 类目编号 */
    private Integer categoryType;

    /* 创建时间 */
    private Date createTime;

    /* 修改时间 */
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

}
