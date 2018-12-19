package io.github.toquery.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringTaskDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTaskDemoApplication.class, args);
	}

}

