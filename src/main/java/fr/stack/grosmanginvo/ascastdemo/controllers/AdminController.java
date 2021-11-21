package fr.stack.grosmanginvo.ascastdemo.controllers;

import fr.stack.grosmanginvo.ascastdemo.services.AsCastService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AsCastService asCastService;

    @PostMapping("/source")
    public void addSource() {
        asCastService.edgeUp();
    }

    @DeleteMapping("/source")
    public void deleteSource() {
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
    public boolean isSource() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }
}
