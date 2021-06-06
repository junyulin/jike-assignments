package homeword2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动注入
 *
 * @author LinJn
 * @since 2021/6/6 23:49
 */
@Configuration
@EnableConfigurationProperties(Student.class)
@ConfigurationPropertiesScan(basePackages = {"homeword2"})
@ConditionalOnProperty(prefix = "linjn", value = "school", havingValue = "true")
public class SchoolAutoConfiguration {

    @Autowired
    private Student student;

    @Bean
    Klass klass() {
        Klass klass = new Klass();
        klass.setStudent(student);
        return klass;
    }

    @Bean
    @ConditionalOnBean(value = Klass.class)
    public School school(Klass klass) {
        School school = new School();
        school.setClass1(klass);
        school.setStudent(student);
        return school;
    }

}
