package fr.stack.grosmanginvo.ascastdemo.controllers;

import fr.stack.grosmanginvo.ascastdemo.configurations.Routes;
import fr.stack.grosmanginvo.ascastdemo.models.MockData;
import fr.stack.grosmanginvo.ascastdemo.models.Source;
import fr.stack.grosmanginvo.ascastdemo.services.AsCastService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(Routes.AS_CAST_ROOT)
@AllArgsConstructor
public class AsCastController {

    private final AsCastService asCastService;

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
    public void del(Source source) {
        this.asCastService.receiveDel(source);
    }

    /**
     * Endpoint called when a source is added in the network
     */
    @PostMapping(Routes.AS_CAST_ADD)
    public void add(Source source) {
        this.asCastService.receiveAdd(source);
    }
}
