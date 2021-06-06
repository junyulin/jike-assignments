package homeword2;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "student")
public class Student {

    private int id = 1;

    private String name = "student";

    private String beanName = "beanStudent";

}
