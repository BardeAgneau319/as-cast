package fr.stack.grosmanginvo.ascastdemo.configurations.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Initializes HTTP client bean
 */
@Configuration
public class RestTemplateBean {

    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
