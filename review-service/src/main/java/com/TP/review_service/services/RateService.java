package com.TP.review_service.services;

import com.TP.review_service.commands.UpdateAverageCommand;
import com.TP.review_service.models.DTO.RateDTO;
import com.TP.review_service.models.PostRate;
import com.TP.review_service.repositories.PostRateRepository;
import com.TP.review_service.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RateService {

    private PostRateRepository postRateRepository;
    private UpdateAverageCommand updateAverageCommand;
    private PostRepository postRepository;

    public RateService(PostRateRepository postRateRepository,
                       UpdateAverageCommand updateAverageCommand,
                       PostRepository postRepository) {
        this.postRateRepository = postRateRepository;
        this.updateAverageCommand = updateAverageCommand;
        this.postRepository = postRepository;
    }

    public boolean insertRate(RateDTO rateDTO) {
        PostRate postRate = PostRate.fromDTO(rateDTO);

        if(!this.postRepository.existsById(rateDTO.postId())) {
            throw new RuntimeException("Post not found");
        }

        postRateRepository.save(postRate);
        updateAverageCommand.execute();

        return true;
    }

}
