package com.projetspring.projet.repositories;

import com.projetspring.projet.entities.Actor;
import com.projetspring.projet.entities.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class ActorRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Test
    void findActorJPQLTest() {
        String surname1 = "Jean";
        String lastname1 = "Dujardin";

        String surname2 = "Pierre";
        String lastname2 = "Dujardin";

        Movie movie1 = new Movie(null, "title", 0f, "synopsis", null);
        Movie movie2 = new Movie(null, "title2", 0f, "synopsis", null);
        Actor actor1 = new Actor(null, surname1, lastname1, Collections.singletonList(movie1));
        Actor actor2 = new Actor(null, surname2, lastname2, Collections.singletonList(movie1));

        movie1.setActors(Collections.singletonList(actor1));
        movie2.setActors(Collections.singletonList(actor2));

        actorRepository.save(actor1);
        actorRepository.save(actor2);
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        Actor actorFetched = actorRepository.findByJPQL(surname1, lastname1);
        Assertions.assertEquals(surname1, actorFetched.getFirstName());
        Assertions.assertEquals(lastname1, actorFetched.getLastName());

        actorFetched = actorRepository.findByJPQL(surname2, lastname2);
        Assertions.assertEquals(surname2, actorFetched.getFirstName());
        Assertions.assertEquals(lastname2, actorFetched.getLastName());
    }
}