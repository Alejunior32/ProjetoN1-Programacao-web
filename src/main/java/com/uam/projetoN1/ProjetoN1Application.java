package com.uam.projetoN1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ProjetoN1Application {

	@Bean
	public WebClient webClient(WebClient.Builder builder ){
		return builder
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(512 * 1024))
				.baseUrl("http://servicodados.ibge.gov.br/")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjetoN1Application.class, args);
	}

}
