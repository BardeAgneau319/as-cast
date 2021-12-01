package fr.stack.grosmanginvo.ascastdemo;

import fr.stack.grosmanginvo.ascastdemo.models.Server;
import fr.stack.grosmanginvo.ascastdemo.services.AsCastService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Methodes to be executed on start-up. Send source status.
 */
@Component
@RequiredArgsConstructor
public class ApplicationStartUp implements ApplicationListener<ContextRefreshedEvent> {

    private final Server server;

    private final AsCastService asCastService;

    private final Logger logger = Logger.getLogger(ApplicationStartUp.class.getName());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.logger.log(Level.FINE, "Executing start-up component");
        if (this.server.isSource()) {
            this.logger.log(Level.FINE, "This node is a source.");
            this.logger.log(Level.FINE, "Sending add event to every neighbour.");
            this.asCastService.setAsSource();
        } else {
            this.logger.log(Level.FINE, "This node is not a source.");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ignored) { }
            this.asCastService.fetchSourceUntilSuccess();
        }
    }
}
