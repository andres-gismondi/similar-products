package com.zara.similarproducts.infraestructure.configuration;

import com.zara.similarproducts.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@ComponentScan(basePackageClasses = App.class)
public class BeanConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        String baseUrl = env.getProperty("rest_client_default_url", "http://localhost:3001");
        return builder.uriTemplateHandler(new DefaultUriBuilderFactory(baseUrl)).build();
    }

}
