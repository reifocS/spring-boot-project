package com.projetspring.projet.services;

import com.projetspring.projet.entities.Actor;
import com.projetspring.projet.entities.Movie;
import com.projetspring.projet.exceptions.MovieCreationWithOverRate;
import com.projetspring.projet.exceptions.MovieCreationWithoutActorsException;
import com.projetspring.projet.exceptions.NoneExistantActorException;
import com.projetspring.projet.repositories.ActorRepository;
import com.projetspring.projet.repositories.MovieRepository;
import com.projetspring.projet.responses.MovieWithActorsDTO;
import com.projetspring.projet.responses.utils.MovieMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public MovieWithActorsDTO addMovie(MovieWithActorsDTO movieWithActorsDTO) throws MovieCreationWithoutActorsException, NoneExistantActorException, MovieCreationWithOverRate {
        if (movieWithActorsDTO.getActors().isEmpty()) {
            throw new MovieCreationWithoutActorsException("Impossible de créer un film sans acteur, relation many to many requise !");
        } else if (movieWithActorsDTO.getRate()<0 || movieWithActorsDTO.getRate()>5){
            throw new MovieCreationWithOverRate("Impossible de créer un film avec une note exclue de [0;5]");
        }
        Movie movie = MovieMapper.movieWithActorsDTOtoMovie(movieWithActorsDTO);
        List<Actor> actors = movie.getActors();
        for (int i = 0; i < actors.size(); ++i) {
            Actor actor = actors.get(i);
            Long actorId = actor.getId();
            if (actorId != null) {
                actor = actorRepository.findById(actorId).orElseThrow(() -> new NoneExistantActorException(String.format("Impossible d'ajouter le film, l'acteur id:%s est inexistant!", actorId)));
                actors.set(i, actor);
            }
            actorRepository.save(actor);
        }
        movieRepository.save(movie);
        return MovieMapper.movieToMovieWithActorsDTO(movie);
    }

    public List<MovieWithActorsDTO> findAll() {
        List<Movie> movies = movieRepository.getAllByJPQL();
        List<MovieWithActorsDTO> movieWithActorsDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            movieWithActorsDTOS.add(MovieMapper.movieToMovieWithActorsDTO(movie));
        }
        return movieWithActorsDTOS;
    }

    public List<MovieWithActorsDTO> getAllMoviesGreaterThan(Float rate) {
        List<Movie> movies = movieRepository.getAllMoviesGreaterThanByJPQL(rate);
        List<MovieWithActorsDTO> movieWithActorsDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            movieWithActorsDTOS.add(MovieMapper.movieToMovieWithActorsDTO(movie));
        }
        return movieWithActorsDTOS;
    }

    public MovieWithActorsDTO findById(Long movieId) {
        return MovieMapper.movieToMovieWithActorsDTO(movieRepository.findByJPQL(movieId));
    }

    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findByJPQL(movieId);
        if (movie == null){
            throw new NoSuchElementException();
        }
        movieRepository.delete(movie);
        List<Actor> actors = movie.getActors();
        for (Actor actor : actors) {
            Actor actorFull = actorRepository.findByIdJPQL(actor.getId());
            if (actorFull == null){
                actorRepository.delete(actor);
            }
        }
    }
}
