package cn.linjn.repository;

import cn.linjn.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Demo class
 *
 * @author LinJn
 * @since 2021/6/27 21:18
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}