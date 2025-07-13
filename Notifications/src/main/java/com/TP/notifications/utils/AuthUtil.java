package com.TP.notifications.utils;

import com.TP.notifications.security.MinimalUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class AuthUtil {
    public static UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MinimalUserDetails user = (MinimalUserDetails) auth.getPrincipal();
        return user.getUserId();
    }
}
