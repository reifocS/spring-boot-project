package com.projetspring.projet.services;

import com.projetspring.projet.entities.Actor;
import com.projetspring.projet.entities.Movie;
import com.projetspring.projet.exceptions.NoneExistantActorException;
import com.projetspring.projet.repositories.ActorRepository;
import com.projetspring.projet.repositories.MovieRepository;
import com.projetspring.projet.responses.ActorWithMoviesDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Repository.class, Service.class}))
class ActorServiceTest {
    private final ActorService actorService;
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ActorServiceTest(ActorService actorService, ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorService = actorService;
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    @Test
    void findByFullName() {
        String firstname = "Jean";
        String lastname1 = "Dujardin";

        Actor actor = new Actor(null, firstname, lastname1, null);
        Movie movie = new Movie(null, "title", 0f, "synopsis", null);
        actor.setMovies(Collections.singletonList(movie));
        movie.setActors(Collections.singletonList(actor));
        actorRepository.save(actor);
        movieRepository.save(movie);
        ActorWithMoviesDTO actorFetched = null;
        try {
            actorFetched = actorService.findByFullName(firstname, lastname1);
        } catch (NoneExistantActorException e) {
            Assertions.fail();
        }

        Assertions.assertEquals(actorFetched.getFirstName(), firstname);
        Assertions.assertEquals(actorFetched.getLastName(), lastname1);

        //TODO test for a false actor
        Assertions.assertThrows(NoneExistantActorException.class, () ->
                actorService.findByFullName(firstname, "unfound")
        );
        Assertions.assertThrows(NoneExistantActorException.class, () ->
                actorService.findByFullName("unfound", lastname1)
        );
    }
}