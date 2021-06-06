package homeword2;

import lombok.Data;

@Data
public class Klass {
    
    private Student student;

    public void dong(){
        System.out.println(student);
    }
    
}
