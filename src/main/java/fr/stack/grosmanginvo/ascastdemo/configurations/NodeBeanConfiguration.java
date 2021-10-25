package fr.stack.grosmanginvo.ascastdemo.configurations;

import fr.stack.grosmanginvo.ascastdemo.models.INode;
import fr.stack.grosmanginvo.ascastdemo.models.Node;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NodeBeanConfiguration {

    private INode node = new Node();

    @Bean("node")
    public INode node() {
        return node;
    }
}
