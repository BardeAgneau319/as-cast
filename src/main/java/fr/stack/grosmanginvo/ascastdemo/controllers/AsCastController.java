package fr.stack.grosmanginvo.ascastdemo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/as-cast")
public class AsCastController {

    @GetMapping("/source")
    public void getSource() {

    }


    /**
     * Endpoint called when a source is deleted in the network
     */
    @PostMapping("/delete-event")
    public void receiveDelete() {

    }

    /**
     * Endpoint called when a source is added in the network
     */
    @PostMapping("/add-event")
    public void receiveAdd() {

    }
}
