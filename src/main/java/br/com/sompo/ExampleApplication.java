package br.com.sompo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories	//TODO indicar o nome base dos seus pacotes
@ComponentScan(basePackages = "br.com.sompo.example.*", basePackageClasses = ExampleApplication.class)
public class ExampleApplication extends SpringBootServletInitializer{


	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ExampleApplication.class);
	}
	
}
