package cn.linjn.domain;

import cn.linjn.entity.Order;
import cn.linjn.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo class
 *
 * @author LinJn
 * @since 2021/6/27 21:19
 */
//@Component
@RequiredArgsConstructor
public class CurdDemo implements ApplicationRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        add();
        delete();
//        System.out.println(get());
    }

    public void add() {
        List<Order> orderList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            Order order = new Order();
            order.setOrderNo("DD254189984");
            order.setUserId(i % 2L);
            order.setStatus((byte) 1);
            order.setPrice("65.21");
            order.setDiscountPrice("54.32");
            orderList.add(order);
        }
        this.orderRepository.saveAll(orderList);
    }

    public void delete() {
        this.orderRepository.deleteById(616045350901755905L);
    }

    public Order get() {
        return this.orderRepository.findById(616045350901755905L)
                .orElseThrow(RuntimeException::new);
    }

    public void update() {
        Order order = get();
        order.setPrice("65.42");
        // 无法使用 save 进行更新
        this.orderRepository.save(order);
    }
}
