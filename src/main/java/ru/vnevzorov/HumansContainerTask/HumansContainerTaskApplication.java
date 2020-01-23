package ru.vnevzorov.HumansContainerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;

@SpringBootApplication
public class HumansContainerTaskApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HumansContainerTaskApplication.class, args);
	}

	@Autowired
	private HumanContainer humanContainer;

	@Override
	public void run(String... args) throws Exception {
		//humanContainer.addHuman("Vika", 19);
		//humanContainer.changeHumanParameters(4, "Masha", 18);
		//humanContainer.deleteHuman(5);
		//humanContainer.getHumanById(4).print();
		//humanContainer.printAllById();
		humanContainer.printAllSorted();
	}
}
