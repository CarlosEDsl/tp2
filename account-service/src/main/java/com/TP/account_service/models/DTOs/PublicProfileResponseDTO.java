package com.TP.account_service.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PublicProfileResponseDTO {
    private String username;
    private String avatarUrl;
    private String bio;
}
