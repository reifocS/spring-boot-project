package com.projetspring.projet.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorWithMoviesDTO extends ActorMiniDTO {
    private List<MovieMiniDTO> movies;

    public ActorWithMoviesDTO(Long id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }
}
