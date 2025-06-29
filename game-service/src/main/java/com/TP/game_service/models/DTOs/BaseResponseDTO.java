package com.TP.game_service.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDTO<T> {
    private Double count;
    private String next;
    private String previous;
    private List<T> results;
}
