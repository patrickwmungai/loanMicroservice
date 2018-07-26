package com.finessence.loan.config;

/**
 *
 * @author patrick
 */
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket api() {
		List<SecurityScheme> schemeList = new ArrayList<>();
		schemeList.add(new BasicAuth("basicAuth"));

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.finessence.loan.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo()).securitySchemes(schemeList);
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"Loan Services",
				"Loan Services API.",
				"v1.0.0",
				"Terms of service",
				"info@finessence.com",
				"License of API",
				"#");
		return apiInfo;
	}

//	@Bean
//	SecurityConfiguration security() {
//		return new SecurityConfiguration(
//				null,
//				null,
//				null, // realm Needed for authenticate button to work
//				null, // appName Needed for authenticate button to work
//				null, // apiKeyValue
//				ApiKeyVehicle.HEADER,
//				"Authorization", // apiKeyName
//				null
//		);
	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, null, "Bearer access_token", ApiKeyVehicle.HEADER, "Authorization", ",");
	}
}
