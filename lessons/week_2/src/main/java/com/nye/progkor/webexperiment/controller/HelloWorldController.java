package com.nye.progkor.webexperiment.controller;

import com.nye.progkor.webexperiment.model.UsernameHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Scope("session")
public class HelloWorldController {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private UsernameHolder usernameHolder;

    @GetMapping(path = "/welcome/{name}")
    public String getWelcome(Model model, @PathVariable("name") String name) {
        LOG.info("In PathVariable Get controller method");
        model.addAttribute("name", name);
        return "hello-world";
    }

    @GetMapping(path = "/welcome")
    public String getWelcome(Model model) {
        LOG.info("In the simple Get controller method");
        model.addAttribute("name", usernameHolder.getName());
        return "hello-world";
    }

    @GetMapping(path = "/welcome-form")
    public String getWelcomeForm() {
        return "hello-world-form";
    }

    @PostMapping(path = "/welcome")
    public String postWelcome(Model model, String name) {
        LOG.info("In the Post controller method");
        usernameHolder.setName(name);
        model.addAttribute("name", name);
        return "hello-world";
    }

}
