package fr.stack.grosmanginvo.ascastdemo.controllers;

import fr.stack.grosmanginvo.ascastdemo.configurations.Routes;
import fr.stack.grosmanginvo.ascastdemo.models.ISource;
import fr.stack.grosmanginvo.ascastdemo.services.AsCastService;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.AS_CAST_ROOT)
@AllArgsConstructor
public class AsCastController {

    private final AsCastService asCastService;

    @GetMapping(Routes.AS_CAST_SOURCE)
    public void getSource() {
        // c'est quoi déjà ?
    }

    /**
     * Endpoint called by a node who want the data
     */
    @GetMapping(Routes.AS_CAST_DATA)
    public void getData() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    /**
     * Endpoint called when a source is deleted in the network
     */
    @PostMapping(Routes.AS_CAST_DELL)
    public void del(ISource source) {
        this.asCastService.receiveDel(source);
    }

    /**
     * Endpoint called when a source is added in the network
     */
    @PostMapping(Routes.AS_CAST_ADD)
    public void add(ISource source) {
        this.asCastService.receiveAdd(source);
    }
}
