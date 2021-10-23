package fr.stack.grosmanginvo.ascastdemo.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/source")
    public void addSource() {

    }

    @DeleteMapping("/source")
    public void deleteSource() {

    }
}
