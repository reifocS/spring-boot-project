package com.projetspring.projet.controllers;


import com.projetspring.projet.exceptions.NoneExistantActorException;
import com.projetspring.projet.responses.ActorWithMoviesDTO;
import com.projetspring.projet.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actor")
    public ResponseEntity<ActorWithMoviesDTO> findActorByFullName(@RequestParam String firstName, @RequestParam String lastName) {
        ActorWithMoviesDTO actor;
        try {
            actor = actorService.findByFullName(firstName, lastName);
        } catch (NoneExistantActorException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actor);
    }

    @GetMapping("")
    public ResponseEntity<List<ActorWithMoviesDTO>> findAllActors() {
        return ResponseEntity.ok(actorService.findAllActors());
    }

    @GetMapping("/actor/{actorId}")
    public ResponseEntity<ActorWithMoviesDTO> findById(@PathVariable("actorId") Long actorId) {
        ActorWithMoviesDTO actor;
        try {
            actor = actorService.findById(actorId);
            return ResponseEntity.ok(actor);
        } catch (NoneExistantActorException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
