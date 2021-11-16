package fr.stack.grosmanginvo.ascastdemo.configurations;

import fr.stack.grosmanginvo.ascastdemo.models.IServer;
import fr.stack.grosmanginvo.ascastdemo.models.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class NodeBeanConfiguration {

    private IServer node = null;

    @Autowired
    private Environment env;

    @Bean("node")
    public IServer node() {
        if (node==null) {
            this.node = Server.builder()
                    .isSource(env.getProperty("IS_SOURCE", Boolean.TYPE, false))
                    .address(env.getProperty("ADDRESS", String.class))
                    .build();
        }

        return node;
    }
}
