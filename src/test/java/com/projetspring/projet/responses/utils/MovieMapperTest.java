package com.projetspring.projet.responses.utils;

import com.projetspring.projet.entities.Actor;
import com.projetspring.projet.entities.Movie;
import com.projetspring.projet.responses.ActorMiniDTO;
import com.projetspring.projet.responses.MovieMiniDTO;
import com.projetspring.projet.responses.MovieWithActorsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MovieMapperTest {
    private final Long id = 1l;
    private final String title = "title";
    private final Float rate = 5f;
    private final String synopsis = "synopsis";

    private final String firstname = "firstname";
    private final String lastname = "lastname";

    private MovieWithActorsDTO movieWithActorsDTO;
    private ActorMiniDTO actorMiniDTO;
    private Movie movie;
    private Actor actor;

    @BeforeEach
    void init() {
        movie = new Movie(id, title, rate, synopsis, null);
        actor = new Actor(id, firstname, lastname, Arrays.asList(movie));
        movie.setActors(Arrays.asList(actor));

        movieWithActorsDTO = new MovieWithActorsDTO(id, title, rate, synopsis);
        actorMiniDTO = new ActorMiniDTO(id, firstname, lastname);
        movieWithActorsDTO.setActors(Arrays.asList(actorMiniDTO));
    }


    @Test
    void movieToMovieWithActorsDTO() {
        Movie movie = MovieMapper.movieWithActorsDTOtoMovie(movieWithActorsDTO);
        Assertions.assertEquals(id, movie.getId());
        Assertions.assertEquals(title, movie.getTitle());
        Assertions.assertEquals(rate, movie.getRate());
        Assertions.assertEquals(synopsis, movie.getSynopsis());
        Assertions.assertEquals(ActorMapper.actorMiniDTOToActor(actorMiniDTO), movie.getActors().get(0));
    }

    @Test
    void movieWithActorsDTOtoMovie() {
        MovieWithActorsDTO movieWithActorsDTO = MovieMapper.movieToMovieWithActorsDTO(movie);
        Assertions.assertEquals(id, movieWithActorsDTO.getId());
        Assertions.assertEquals(title, movieWithActorsDTO.getTitle());
        Assertions.assertEquals(rate, movieWithActorsDTO.getRate());
        Assertions.assertEquals(synopsis, movieWithActorsDTO.getSynopsis());
        Actor actor = ActorMapper.actorMiniDTOToActor(movieWithActorsDTO.getActors().get(0));
        Assertions.assertEquals(this.actor.getId(), actor.getId());
        Assertions.assertEquals(this.actor.getFirstName(), actor.getFirstName());
        Assertions.assertEquals(this.actor.getLastName(), actor.getLastName());
    }

    @Test
    void movieToMovieMiniDTO() {
        MovieMiniDTO movieMiniDTO = MovieMapper.movieToMovieMiniDTO(movie);
        Assertions.assertEquals(id, movieMiniDTO.getId());
        Assertions.assertEquals(title, movieMiniDTO.getTitle());
        Assertions.assertEquals(rate, movieMiniDTO.getRate());
        Assertions.assertEquals(synopsis, movieMiniDTO.getSynopsis());
    }
}