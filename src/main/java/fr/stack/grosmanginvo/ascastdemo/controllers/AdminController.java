package fr.stack.grosmanginvo.ascastdemo.controllers;

import fr.stack.grosmanginvo.ascastdemo.models.Source;
import fr.stack.grosmanginvo.ascastdemo.services.AsCastService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AsCastService asCastService;
    private final Logger logger = Logger.getLogger(AsCastService.class.getName());

    @GetMapping("/source")
    public Source getSource() {
        return asCastService.getSource();
    }

    @PostMapping("/source")
    public void addSource() {
        this.logger.log(Level.INFO, "Enabling source");
        asCastService.edgeUp();
    }

    @DeleteMapping("/source")
    public void deleteSource() {
        this.logger.log(Level.INFO, "Disabling source");
        asCastService.edgeDown();
    }

    /**
     * Set or unset a node a source
     * @param isSource Whether the node is a source
     */
    @PostMapping("/is-source")
    public void setIsSource(@RequestParam boolean isSource) {
        if (isSource) asCastService.edgeUp();
        else asCastService.edgeDown();
    }

    @GetMapping("is-source")
    public boolean isSource() {
        return asCastService.isSource();
    }
}
