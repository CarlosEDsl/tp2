package com.TP.review_service.repositories;

import com.TP.review_service.models.PostRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRateRepository extends JpaRepository<PostRate, PostRate.RateId> {
    List<PostRate> findAllByIdPostId(UUID postId);
}
