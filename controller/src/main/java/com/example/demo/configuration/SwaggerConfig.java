package com.example.demo.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Clase de configuración del swagger
 * 
 * @author nosipova
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
   

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/shoes/**"))
                .build()
                .apiInfo(metaInfo());
    }


	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo(
                "Shoe Shop API",
                "Shoe Shop API",
                "1.0",
                "Terms of Service",
                new Contact("Paradigma Digital", "https://git.paradigmadigital.com/",
                        "nosipova@paradigmadigital.com"),
                "Apache License Version 2.0",
                " http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList()
        );

        return apiInfo;
    }
	
	@Bean
	public LinkDiscoverers discoverers() {
		List<LinkDiscoverer> plugins = new ArrayList<>();
		plugins.add(new CollectionJsonLinkDiscoverer());
		return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

	}
	
}