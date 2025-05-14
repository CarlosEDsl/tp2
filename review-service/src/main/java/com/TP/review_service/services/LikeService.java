package com.TP.review_service.services;

import com.TP.review_service.commands.UpdateAverageCommand;
import com.TP.review_service.repositories.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    UpdateAverageCommand updateAverageCommand;

    public LikeService(LikeRepository likeRepository, UpdateAverageCommand updateAverageCommand) {
        this.likeRepository = likeRepository;
        this.updateAverageCommand = updateAverageCommand;
    }

}
