package cn.linjn.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单表
 *
 * @author LinJn
 * @since 2021/6/27 21:13
 */
@Table(name = "`order`")
@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNo;

    private Long userId;

    private byte status;

    private String price;

    private String discountPrice;

    // Date 类型的数据可以插入，但是查询报错
    // private Date createTime;

}
