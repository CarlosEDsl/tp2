package com.TP.game_service.services.adapters;

import com.TP.game_service.models.PlatformAdapted;
import com.TP.game_service.models.Rawg.*;
import com.TP.game_service.models.StoreAdapted;
import org.springframework.stereotype.Service;

@Service
public class StoreAdapter {
    public StoreAdapted adapt(StoreInfo storeInfo) {
        return new StoreAdapted(storeInfo.getStore().getId(), storeInfo.getStore().getName());
    }

    public StoreAdapted adapt(StoreComplete storeComplete) {
        return new StoreAdapted(storeComplete.getId(), storeComplete.getName());
    }
}
