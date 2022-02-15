package com.projetspring.projet.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetspring.projet.entities.Actor;
import com.projetspring.projet.exceptions.NoneExistantActorException;
import com.projetspring.projet.responses.ActorWithMoviesDTO;
import com.projetspring.projet.responses.utils.ActorMapper;
import com.projetspring.projet.services.ActorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ActorController.class)
public class ActorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorService actorService;

    private static final String SERVER_URL = "http://localhost:8080";
    private static final String CONTROLLER_BASE_URL = SERVER_URL + "/api/actors";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static Actor actor;

    private static final Long id = null;
    private static final String firstname = "firstname";
    private static final String lastname = "lastname";

    @BeforeAll
    static void init() {
        actor = new Actor(id, firstname, lastname, new ArrayList<>());
    }

    @Test
    void findActorByFullNameTest() throws Exception {
        Mockito.when(actorService.findByFullName(actor.getFirstName(), actor.getLastName())).thenReturn(ActorMapper.actorToActorWithMoviesDTO(actor));
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_BASE_URL + "/actor")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", actor.getFirstName())
                .param("lastName", actor.getLastName()))
                .andExpect(MockMvcResultMatchers.status().isOk()
                ).andReturn();
        ActorWithMoviesDTO dto = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), ActorWithMoviesDTO.class);
        Assertions.assertEquals(actor.getFirstName(), dto.getFirstName());
        Assertions.assertEquals(actor.getLastName(), dto.getLastName());
    }

    @Test
    void findActorByFullNameThrowNoneExistantActorTest() throws Exception {
        Mockito.when(actorService.findByFullName(actor.getFirstName(), actor.getLastName())).thenThrow(NoneExistantActorException.class);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_BASE_URL + "/actor")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", actor.getFirstName())
                .param("lastName", actor.getLastName()))
                .andExpect(MockMvcResultMatchers.status().isNotFound()
                ).andReturn();
    }

    @Test
    void findAllActors() throws Exception {
        Actor actor1 = actor.toBuilder().build();
        actor1.setFirstName("actor1");
        Mockito.when(actorService.findAllActors()).thenReturn(Arrays.asList(
                ActorMapper.actorToActorWithMoviesDTO(actor),
                ActorMapper.actorToActorWithMoviesDTO(actor1)
        ));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()
                ).andReturn();

        ArrayList<ActorWithMoviesDTO> actorWithMoviesDTOS = MAPPER.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        Assertions.assertEquals(2, actorWithMoviesDTOS.size());
        Assertions.assertEquals(actor.getFirstName(), actorWithMoviesDTOS.get(0).getFirstName());
        Assertions.assertEquals(actor.getLastName(), actorWithMoviesDTOS.get(0).getLastName());
        Assertions.assertEquals(actor1.getFirstName(), actorWithMoviesDTOS.get(1).getFirstName());
        Assertions.assertEquals(actor1.getLastName(), actorWithMoviesDTOS.get(1).getLastName());
    }

    @Test
    void findById() throws Exception {
        Long id = 13L;
        actor.setId(id);
        Mockito.when(actorService.findById(actor.getId())).thenReturn(ActorMapper.actorToActorWithMoviesDTO(actor));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_BASE_URL + "/actor/" + actor.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()
                ).andReturn();

        ActorWithMoviesDTO actorWithMoviesDTO = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), ActorWithMoviesDTO.class);

        Assertions.assertEquals(actor.getId(), actorWithMoviesDTO.getId());
        Assertions.assertEquals(actor.getFirstName(), actorWithMoviesDTO.getFirstName());
        Assertions.assertEquals(actor.getLastName(), actorWithMoviesDTO.getLastName());
    }

    @Test
    void findByIdThrowNoneExistantActorTest() throws Exception {
        Long id = 13L;
        actor.setId(id);
        Mockito.when(actorService.findById(actor.getId())).thenThrow(NoneExistantActorException.class);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(CONTROLLER_BASE_URL + "/actor/" + actor.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound()
                ).andReturn();
    }
}
