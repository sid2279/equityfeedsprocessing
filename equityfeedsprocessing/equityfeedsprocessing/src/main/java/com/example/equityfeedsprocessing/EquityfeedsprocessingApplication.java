package com.example.equityfeedsprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableJms
@EnableScheduling
@EnableSwagger2
public class EquityfeedsprocessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquityfeedsprocessingApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		// return a prepared Docket instance.
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/equityFeeds/**"))
				.apis(RequestHandlerSelectors.basePackage("com.example.equityfeedsprocessing"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {

		return new ApiInfo(
				"Equity Feeds Processing Search API",
				"Sample API for Equity Feeds Processing",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Siddharth Bhargava", "http://javabrains.io", "siddharth.bhargava@publicissapient.com"),
				"API License",
				"http://javabrains.io",
				Collections.emptyList());
	}

}
