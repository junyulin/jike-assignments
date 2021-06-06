package homeword2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SchoolApplication
 *
 * @author LinJn
 * @since 2021/6/7 0:10
 */
@SpringBootApplication
@RestController
@RequestMapping()
public class SchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }

    @Autowired
    private School school;

    @GetMapping(value = "index")
    public School index() {
        return school;
    }

}
