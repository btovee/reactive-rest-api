package com.btovee.reactiverestservice.config.ClientRest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientRestConfig {

    @Bean
    public WebClient webClient() { return WebClient.builder().build(); }

}
