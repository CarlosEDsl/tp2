package com.TP.game_service.mappers;

import com.TP.game_service.models.Rawg.*;
import com.TP.game_service.models.StoreAdapted;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {
    public StoreAdapted adapt(StoreInfo storeInfo) {
        return new StoreAdapted(storeInfo.getStore().getId(), storeInfo.getStore().getName());
    }

    public StoreAdapted adapt(StoreComplete storeComplete) {
        return new StoreAdapted(storeComplete.getId(), storeComplete.getName());
    }
}
