package com.projetspring.projet.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieWithActorsDTO extends MovieMiniDTO {
    private List<ActorMiniDTO> actors;

    public MovieWithActorsDTO(Long id, String title, Float rate, String synopsis) {
        super(id, title, rate, synopsis);
    }
}
