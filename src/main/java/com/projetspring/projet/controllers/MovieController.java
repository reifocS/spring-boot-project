package com.projetspring.projet.controllers;


import com.projetspring.projet.exceptions.MovieCreationWithOverRate;
import com.projetspring.projet.exceptions.MovieCreationWithoutActorsException;
import com.projetspring.projet.exceptions.NoneExistantActorException;
import com.projetspring.projet.responses.MovieWithActorsDTO;
import com.projetspring.projet.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<MovieWithActorsDTO> findById(@PathVariable("movieId") Long movieId) {
        MovieWithActorsDTO movie = movieService.findById(movieId);
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/add")
    public ResponseEntity<MovieWithActorsDTO> addMovie(@RequestBody MovieWithActorsDTO movie) {
        try {
            movie = movieService.addMovie(movie);
        } catch (MovieCreationWithoutActorsException | NoneExistantActorException | MovieCreationWithOverRate e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(movie);
    }

    @GetMapping("")
    public ResponseEntity<List<MovieWithActorsDTO>> findAll() {
        List<MovieWithActorsDTO> movies = movieService.findAll();
        return ResponseEntity.ok(movies);
    }

    @DeleteMapping("/movie/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable("movieId") Long movieId) {
        try {
            movieService.deleteMovie(movieId);
            return ResponseEntity.ok().build();
        }catch(NoSuchElementException e)  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sort")
    public ResponseEntity<List<MovieWithActorsDTO>> getAllMoviesGreaterThan(@RequestParam Float rate) {
        List<MovieWithActorsDTO> movies = movieService.getAllMoviesGreaterThan(rate);
        return ResponseEntity.ok(movies);
    }
}
