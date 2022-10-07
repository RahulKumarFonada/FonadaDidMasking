package com.fonada.masking.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer{
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	@Bean
	  public Docket produceApi() {

	    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
	        .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
	        .build();
	  }

	  // Describe your apis

	  /**
	   * Api info.
	   *
	   * @return the api info
	   */
	  private ApiInfo apiInfo() {
	    return new ApiInfoBuilder().title("Did Masking Rest Api's")
	        .description("This page lists all the rest apis for App.").version("0.1.1").build();
	  }
}
