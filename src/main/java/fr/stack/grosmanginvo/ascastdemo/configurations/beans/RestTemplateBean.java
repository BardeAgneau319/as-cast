package fr.stack.grosmanginvo.ascastdemo.configurations.beans;

import fr.stack.grosmanginvo.ascastdemo.errors.RestTemplateErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Initializes HTTP client bean
 */
@Configuration
public class RestTemplateBean {

    @Bean("restTemplate")
    public RestTemplate restTemplate(@Autowired RestTemplateBuilder builder) {
        return builder.errorHandler(new RestTemplateErrorHandler()).build();

    }
}
