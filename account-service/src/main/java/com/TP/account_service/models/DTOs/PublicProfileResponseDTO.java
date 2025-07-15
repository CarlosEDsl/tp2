package com.TP.account_service.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PublicProfileResponseDTO {
    public String username;
    public String avatarUrl;
    public String bio;
}
