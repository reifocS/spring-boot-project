package com.projetspring.projet.repositories;

import com.projetspring.projet.entities.Actor;
import com.projetspring.projet.entities.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)

/**
 * @link https://reflectoring.io/spring-boot-data-jpa-test/
 */
@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Test
    void getAllByJPQLTest() {
        Movie movie = new Movie(null, "title", 0f, "synopsis", null);
        Movie movie1 = new Movie(null, "title2", 0f, "synopsis", null);
        Actor actor = new Actor(null, "Jean", "Dujardin", Arrays.asList(movie, movie1));
        movie.setActors(Arrays.asList(actor));
        movie1.setActors(Arrays.asList(actor));

        actorRepository.save(actor);
        movieRepository.save(movie);
        movieRepository.save(movie1);

        List<Movie> movieList = movieRepository.getAllByJPQL();
        Assertions.assertEquals(2, movieList.size());
        Assertions.assertEquals(movie, movieList.get(0));
        Assertions.assertEquals(movie1, movieList.get(1));
    }
}