package fr.stack.grosmanginvo.ascastdemo;

import fr.stack.grosmanginvo.ascastdemo.models.IServer;
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

    private final IServer server;

    private final AsCastService asCastService;

    private final Logger logger = Logger.getLogger(ApplicationStartUp.class.getName());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.logger.log(Level.INFO, "Start-up component");
        if (this.server.isSource()) {
            this.logger.log(Level.INFO, "Edge up!");
            this.asCastService.edgeUp();
        }
    }
}
