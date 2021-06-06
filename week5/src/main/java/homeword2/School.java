package homeword2;

import lombok.Data;

@Data
public class School implements ISchool {
    
    Klass class1;
    
    Student student;

    @Override
    public void ding() {
        System.out.println(class1);
        System.out.println(student);
    }
}
