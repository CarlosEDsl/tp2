package com.TP.review_service.models;

import com.TP.review_service.models.DTO.RateDTO;
import com.TP.review_service.models.enums.Rate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "post_rate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRate {

    @EmbeddedId
    private RateId id;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private Rate rate;

    public static PostRate fromDTO(RateDTO dto) {
        RateId id = new RateId(dto.userId(), dto.postId());
        return new PostRate(id, Rate.fromValue(dto.rate()));
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RateId implements Serializable {
        @Column(nullable = false)
        private UUID userId;

        @Column(nullable = false)
        private UUID postId;
    }
}
