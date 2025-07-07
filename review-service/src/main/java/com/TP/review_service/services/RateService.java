package com.TP.review_service.services;

import com.TP.review_service.commands.UpdateAverageCommand;
import com.TP.review_service.models.DTO.RateDTO;
import com.TP.review_service.models.PostRate;
import com.TP.review_service.repositories.PostRateRepository;
import com.TP.review_service.repositories.PostRepository;
import com.TP.review_service.security.AuthValidator;
import org.springframework.stereotype.Service;

@Service
public class RateService {

    private final PostRateRepository postRateRepository;
    private final PostRepository postRepository;

    public RateService(PostRateRepository postRateRepository,
                       PostRepository postRepository) {
        this.postRateRepository = postRateRepository;
        this.postRepository = postRepository;
    }

    public void insertRate(RateDTO rateDTO) {
        PostRate postRate = PostRate.fromDTO(rateDTO);

        if (!this.postRepository.existsById(rateDTO.postId())) {
            throw new RuntimeException("Post not found");
        }

        postRateRepository.save(postRate);

        UpdateAverageCommand updateAverageCommand = new UpdateAverageCommand(rateDTO.postId(), postRepository, postRateRepository);
        updateAverageCommand.execute();

    }

    public void removeRate(PostRate.RateId rateId) {

        AuthValidator.checkIfUserIsAuthorized(rateId.getUserId());

        this.postRateRepository.deleteById(rateId);

        UpdateAverageCommand updateAverageCommand = new UpdateAverageCommand(rateId.getPostId(), postRepository, postRateRepository);
        updateAverageCommand.execute();
    }

}
