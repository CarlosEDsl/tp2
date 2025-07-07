package com.TP.review_service.controllers;

import com.TP.review_service.models.DTO.RateDTO;
import com.TP.review_service.models.PostRate;
import com.TP.review_service.security.AuthValidator;
import com.TP.review_service.services.RateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rates")
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping
    public ResponseEntity<Void> insertRating(@RequestBody RateDTO rateDTO) {

        AuthValidator.checkIfUserIsAuthorized(rateDTO.userId());

        rateService.insertRate(rateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeRating(@RequestBody PostRate.RateId rateId) {

        AuthValidator.checkIfUserIsAuthorized(rateId.getUserId());

        rateService.removeRate(rateId);
        return ResponseEntity.noContent().build();
    }


}
