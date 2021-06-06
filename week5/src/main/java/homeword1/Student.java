package homeword1;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 用来注入的类
 *
 * @author LinJn
 * @since 2021/6/6 21:40
 */
@Component
@Data
public class Student {

    private Integer age;

    private String name;

}
