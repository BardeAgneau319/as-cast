package fr.stack.grosmanginvo.ascastdemo.controllers;

import fr.stack.grosmanginvo.ascastdemo.configurations.Routes;
import fr.stack.grosmanginvo.ascastdemo.models.MockData;
import fr.stack.grosmanginvo.ascastdemo.models.Source;
import fr.stack.grosmanginvo.ascastdemo.services.AsCastService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(Routes.AS_CAST_ROOT)
@AllArgsConstructor
public class AsCastController {

    private final AsCastService asCastService;
    private final Logger logger = Logger.getLogger(AsCastService.class.getName());

    /**
     * Endpoint to get the source of the node. Used by neighbor nodes on start-up.
     * @return
     */
    @GetMapping(Routes.AS_CAST_SOURCE)
    public Optional<Source> getSource() {
        return this.asCastService.forwardSource();
    }

    /**
     * Endpoint called by a node who want the data
     */
    @GetMapping(Routes.AS_CAST_DATA)
    public MockData getData() {
        return asCastService.getData();
    }

    /**
     * Endpoint called when a source is deleted in the network
     */
    @PostMapping(Routes.AS_CAST_DELL)
    public void del(@RequestBody Source source) {
        this.logger.log(Level.INFO, "Received DEL message from " + source.getPath().get(0).getAddress());
        this.asCastService.receiveDel(source);
    }

    /**
     * Endpoint called when a source is added in the network
     */
    @PostMapping(Routes.AS_CAST_ADD)
    public void add(@RequestBody Source source) {
        this.logger.log(Level.INFO, "Received ADD message from " + source.getPath().get(0).getAddress());
        this.asCastService.receiveAdd(source);
    }
}
