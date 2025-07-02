package com.TP.review_service.models.DTO;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
    private Long gameId;
    private String title;
    private String content;
    private String imageURL;
}
