package com.TP.game_service.services.adapters;

import com.TP.game_service.models.PlatformAdapted;
import com.TP.game_service.models.Rawg.PlatformComplete;
import com.TP.game_service.models.Rawg.PlatformInfo;
import org.springframework.stereotype.Service;

@Service
public class PlatformAdapter {

    public PlatformAdapted adapt(PlatformInfo platformInfo) {
        return new PlatformAdapted(platformInfo.getPlatform().getId(), platformInfo.getPlatform().getName());
    }

    public PlatformAdapted adapt(PlatformComplete platformComplete) {
        return new PlatformAdapted(platformComplete.getId(), platformComplete.getName());
    }
}
