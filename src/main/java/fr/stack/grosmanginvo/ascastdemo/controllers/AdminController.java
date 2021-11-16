package fr.stack.grosmanginvo.ascastdemo.controllers;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/source")
    public void addSource() {

    }

    @DeleteMapping("/source")
    public void deleteSource() {

    }

    /**
     * Set or unset a node a source
     * @param isSource Whether the node is a source
     */
    @PostMapping("/is-source")
    public void setIsSource(@RequestParam boolean isSource) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    @GetMapping("is-source")
    public boolean isSource() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }
}
