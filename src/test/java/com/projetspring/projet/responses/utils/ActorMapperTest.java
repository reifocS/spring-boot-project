package com.projetspring.projet.responses.utils;

import com.projetspring.projet.entities.Actor;
import com.projetspring.projet.entities.Movie;
import com.projetspring.projet.responses.ActorMiniDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@ExtendWith(SpringExtension.class)

public class ActorMapperTest {

    @Test
    void actorToActorMiniDTOTest() {
        String firstname1 = "Jean";
        String lastname1 = "Dujardin";
        Actor actor1 = new Actor();
        actor1.setFirstName(firstname1);
        actor1.setLastName(lastname1);

        Movie movie = new Movie(null, "title", 0f, "synopsis", Collections.singletonList(actor1));

        actor1.setMovies(Collections.singletonList(movie));
        ActorMiniDTO actorMiniDTO = ActorMapper.actorToActorMiniDTO(actor1);
        Assertions.assertEquals(actorMiniDTO.getFirstName(), actor1.getFirstName());
    }
}