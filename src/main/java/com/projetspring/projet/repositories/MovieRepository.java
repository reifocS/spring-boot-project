package com.projetspring.projet.repositories;

import com.projetspring.projet.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT DISTINCT m FROM Movie AS m JOIN FETCH m.actors")
    List<Movie> getAllByJPQL();

    @Query("SELECT m FROM Movie AS m JOIN FETCH m.actors where m.rate >= ?1")
    List<Movie> getAllMoviesGreaterThanByJPQL(Float rate);

    @Query("SELECT m FROM Movie AS m JOIN FETCH m.actors WHERE m.id=:id")
    Movie findByJPQL(Long id);
}
