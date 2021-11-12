package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import reactor.netty.http.server.HttpServer;

@EnableWebFlux
@SpringBootApplication
@ComponentScan("com.example.demo")
@EnableAutoConfiguration(exclude = { WebMvcAutoConfiguration.class })
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class MultipleCoreImplemSampleApplication {

	@Value("${server.port:8080}")
	private int port = 8080;

	public static void main(String[] args) throws Exception {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				MultipleCoreImplemSampleApplication.class)) {
			context.getBean(HttpServer.class).bindNow().onDispose().block();
		}
	}

	@Profile("default")
	@Bean
	public HttpServer nettyHttpServer(ApplicationContext context) {
		HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
		HttpServer httpServer = HttpServer.create().host("localhost").port(this.port);
		return httpServer.handle(adapter);
	}
}