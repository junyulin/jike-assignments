package homeword1;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Demo class
 *
 * @author LinJn
 * @since 2021/6/6 21:50
 */
public class GetStudentBeans {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student = (Student) context.getBean("student");
        Student student1 = (Student) context.getBean("student1");
        System.out.println(student);
        System.out.println(student1);
    }

}
