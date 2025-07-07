package com.TP.review_service.security;

import com.TP.review_service.exceptions.custom.AccessDeniedException;
import com.TP.review_service.utils.AuthUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class AuthValidator {

    public static void checkIfUserIsAuthorized(UUID requestedId) {
        System.out.println(requestedId);
        System.out.println(getCurrentUserId());
        UUID authId = getCurrentUserId();

        if (!authId.equals(requestedId)) {
            throw new AccessDeniedException("Not permission enough to access this");
        }
    }

    public static UUID getCurrentUserId() {
        return AuthUtil.getCurrentUserId();
    }
}

