package com.TP.game_service.models.Rawg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlatformInfo {
    private Platform platform;
    private String releasedAt;
    private Requirements requirementsEn;
    private Requirements requirementsRu;
}
