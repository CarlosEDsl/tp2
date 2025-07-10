package com.TP.notifications.security;


import com.TP.notifications.utils.AuthUtil;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

public class AuthValidator {

    public static void checkIfUserIsAuthorized(UUID requestedId) throws AccessDeniedException {
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

