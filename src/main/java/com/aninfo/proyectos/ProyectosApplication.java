package com.aninfo.proyectos;

import com.google.common.base.Predicates;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2
@SpringBootApplication
@CrossOrigin(origins = "*", allowedHeaders = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@Controller
public class ProyectosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectosApplication.class, args);
	}

	@RequestMapping("/")
	public String index() {
		return "html/index.html";
	}

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any())
				.paths(Predicates.not(PathSelectors.regex("/error")))
				.paths(Predicates.not(PathSelectors.regex("/")))
				.paths(PathSelectors.any())
				.build();
	}
}