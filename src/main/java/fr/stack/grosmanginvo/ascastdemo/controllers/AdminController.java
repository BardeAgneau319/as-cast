package fr.stack.grosmanginvo.ascastdemo.controllers;

import fr.stack.grosmanginvo.ascastdemo.configurations.Routes;
import fr.stack.grosmanginvo.ascastdemo.models.Node;
import fr.stack.grosmanginvo.ascastdemo.models.Source;
import fr.stack.grosmanginvo.ascastdemo.services.AsCastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(Routes.ADMIN_ROOT)
@RequiredArgsConstructor
public class AdminController {

    private final AsCastService asCastService;
    private final Logger logger = Logger.getLogger(AsCastService.class.getName());

    @GetMapping(Routes.ADMIN_SOURCE)
    public Source getSource() {
        return asCastService.getSource();
    }

    @PostMapping(Routes.ADMIN_SOURCE)
    public void addSource() {
        this.logger.log(Level.INFO, "Enabling source");
        asCastService.setAsSource();
    }

    @DeleteMapping(Routes.ADMIN_SOURCE)
    public void deleteSource() {
        this.logger.log(Level.INFO, "Disabling source");
        asCastService.unsetAsSource();
    }

    /**
     * Set or unset a node a source
     * @param isSource Whether the node is a source
     */
    @PostMapping(Routes.ADMIN_IS_SOURCE)
    public void setIsSource(@RequestParam boolean isSource) {
        if (isSource) asCastService.setAsSource();
        else asCastService.unsetAsSource();
    }

    @GetMapping(Routes.ADMIN_IS_SOURCE)
    public boolean isSource() {
        return asCastService.isSource();
    }

    @PostMapping(Routes.ADMIN_NEIGHBORS)
    public void addNeighbor(@RequestBody Node neighbor) {
        this.asCastService.edgeUp(neighbor);
    }

    @DeleteMapping(Routes.ADMIN_NEIGHBORS+"/{port}")
    public void deleteNeighbor(@PathVariable int port) {
        String address = String.format("http://localhost:%d", port);
        Node neighbor = new Node(address);
        this.asCastService.edgeDown(neighbor);
    }

    @GetMapping(Routes.ADMIN_NEIGHBORS)
    public List<Node> getNeighbors() {
        return this.asCastService.getNeighbors();
    }
}
