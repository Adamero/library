package pl.edu.wat.testowy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.wat.testowy.reflection.FieldInformation;
import pl.edu.wat.testowy.reflection.Reflection;


@SpringBootApplication
public class TestowyApplication {

    public static void main(String[] args) {

        FieldInformation fieldInformation = new FieldInformation();
        Reflection.apply(fieldInformation.getFieldName(), fieldInformation.getFieldType());

        SpringApplication.run(TestowyApplication.class, args);
    }

}
