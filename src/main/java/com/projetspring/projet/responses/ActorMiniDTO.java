package com.projetspring.projet.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActorMiniDTO {
    private Long id;
    private String firstName;
    private String lastName;

    public ActorMiniDTO() {}
}
