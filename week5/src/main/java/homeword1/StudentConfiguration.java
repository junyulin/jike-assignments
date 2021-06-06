package homeword1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 方式3：使用注解 @Configuration 与 @Bean  进行注入
 *
 * @author LinJn
 * @since 2021/6/6 21:48
 */
@Configuration
public class StudentConfiguration {

    @Bean
    public Student student() {
        Student student = new Student();
        student.setName("linjn");
        student.setAge(24);
        return student;
    }

}
