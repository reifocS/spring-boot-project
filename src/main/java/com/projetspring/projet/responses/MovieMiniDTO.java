package com.projetspring.projet.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieMiniDTO {
    private Long id;
    private String title;
    private Float rate;
    private String synopsis;

    public MovieMiniDTO() {
    }
}
