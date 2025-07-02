package com.TP.review_service.controllers;

import com.TP.review_service.models.DTO.PostRequestDTO;
import com.TP.review_service.security.MinimalUserDetails;
import com.TP.review_service.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<?> createPost(@RequestBody PostRequestDTO request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MinimalUserDetails user = (MinimalUserDetails) auth.getPrincipal();

            UUID userId = user.getUserId();
            if(request.getGameId() <= 0) {
                return ResponseEntity.badRequest().body("ID do jogo não pode ser negativo ou igual a zero");
            }

            postService.createPost(request, userId);

            return ResponseEntity.ok().build();
        } catch (HttpMessageNotReadableException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar os dados. Verifique o formato da requisição.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno. Tente novamente mais tarde.");
        }
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}
