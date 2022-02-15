package com.projetspring.projet.repositories;

import com.projetspring.projet.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("SELECT a FROM Actor AS a JOIN FETCH a.movies WHERE a.firstName=:firstName and a.lastName=:lastName")
    Actor findByJPQL(String firstName, String lastName);

    @Query("SELECT DISTINCT a FROM Actor AS a JOIN FETCH a.movies")
    List<Actor> findAllByJPQL();

    @Query("SELECT a FROM Actor AS a JOIN FETCH a.movies WHERE a.id=:id")
    Actor findByIdJPQL(Long id);
}
