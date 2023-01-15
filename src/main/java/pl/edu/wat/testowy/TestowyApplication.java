package pl.edu.wat.testowy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.wat.testowy.reflection.FieldInformation;
import pl.edu.wat.testowy.reflection.Reflection;


@SpringBootApplication
public class TestowyApplication {

	public static void main(String[] args) {
		//tak jak tu korzystam z mechanizmu refleksji tak wystarczy utworzyc zalozmy 10 obiektow, gdzie podajemy nazwe i typ zmiennej
		FieldInformation fieldInformation = new FieldInformation();
		Reflection.apply(fieldInformation.getFieldName(),fieldInformation.getFieldType());
		//Reflection.apply("test2");
		SpringApplication.run(TestowyApplication.class, args);
	}

}
