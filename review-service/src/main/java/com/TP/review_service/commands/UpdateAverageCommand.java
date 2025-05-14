package com.TP.review_service.commands;

import com.TP.review_service.models.Post;
import com.TP.review_service.models.PostRate;
import com.TP.review_service.repositories.PostRateRepository;
import com.TP.review_service.repositories.PostRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UpdateAverageCommand implements Command {

    private final UUID postId;
    private final PostRepository postRepo;
    private final PostRateRepository rateRepo;

    public UpdateAverageCommand(UUID postId, PostRepository postRepo, PostRateRepository rateRepo) {
        this.postId = postId;
        this.postRepo = postRepo;
        this.rateRepo = rateRepo;
    }


    @Override
    public void execute() {
        List<PostRate> rates = rateRepo.findAllByIdPostId(postId);
        double avg = rates.stream().mapToInt(r -> (int) r.getRate().value).average().orElse(0);
        Post post = postRepo.findById(postId).orElseThrow();
        post.setRatingAVG(avg);
        postRepo.save(post);
    }
}
