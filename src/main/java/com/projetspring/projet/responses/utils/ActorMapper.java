package com.projetspring.projet.responses.utils;

import com.projetspring.projet.entities.Actor;
import com.projetspring.projet.entities.Movie;
import com.projetspring.projet.responses.ActorMiniDTO;
import com.projetspring.projet.responses.ActorWithMoviesDTO;
import com.projetspring.projet.responses.MovieMiniDTO;

import java.util.ArrayList;
import java.util.List;

public class ActorMapper {
    public static final ActorMiniDTO actorToActorMiniDTO(Actor actor) {
        return new ActorMiniDTO(actor.getId(), actor.getFirstName(), actor.getLastName());
    }

    private ActorMapper(){}
    public static Actor actorMiniDTOToActor(ActorMiniDTO actorMiniDTO) {
        Actor a = new Actor();
        a.setFirstName(actorMiniDTO.getFirstName());
        a.setLastName(actorMiniDTO.getLastName());
        if (actorMiniDTO.getId() != null){
            a.setId(actorMiniDTO.getId());
        }
        return a;
    }

    public static ActorWithMoviesDTO actorToActorWithMoviesDTO(Actor actor) {
        ActorWithMoviesDTO a = new ActorWithMoviesDTO();
        a.setFirstName(actor.getFirstName());
        a.setLastName(actor.getLastName());
        a.setId(actor.getId());
        List<MovieMiniDTO> moviesDtos = new ArrayList<>();
        for (Movie m : actor.getMovies()) {
            moviesDtos.add(MovieMapper.movieToMovieMiniDTO(m));
        }
        a.setMovies(moviesDtos);
        return a;
    }
}
