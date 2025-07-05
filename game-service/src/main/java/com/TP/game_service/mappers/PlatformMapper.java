package com.TP.game_service.mappers;

import com.TP.game_service.models.PlatformAdapted;
import com.TP.game_service.models.Rawg.PlatformComplete;
import com.TP.game_service.models.Rawg.PlatformInfo;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapper {
    public PlatformAdapted adapt(PlatformInfo platformInfo) {
        return new PlatformAdapted(platformInfo.getPlatform().getId(), platformInfo.getPlatform().getName());
    }

    public PlatformAdapted adapt(PlatformComplete request) {
        return new PlatformAdapted(request.getId(), request.getName());
    }
}
