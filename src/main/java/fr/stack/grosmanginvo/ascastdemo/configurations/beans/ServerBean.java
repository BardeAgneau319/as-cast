package fr.stack.grosmanginvo.ascastdemo.configurations.beans;

import fr.stack.grosmanginvo.ascastdemo.models.IServer;
import fr.stack.grosmanginvo.ascastdemo.models.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Initializes node configuration from environment variables
 */
@Configuration
public class ServerBean {

    private IServer server = null;

    @Autowired
    private Environment env;

    @Bean("server")
    public IServer server() {
        if (server ==null) {
            this.server = Server.builder()
                    .isSource(env.getProperty("IS_SOURCE", Boolean.TYPE, false))
                    .address(env.getProperty("ADDRESS", String.class))
                    .build();
        }

        return server;
    }
}
