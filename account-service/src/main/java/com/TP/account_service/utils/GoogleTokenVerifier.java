package com.TP.account_service.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleTokenVerifier {

    private static String CLIENT_ID = "377874780445-6k5jtnjc89jvusg7um6ebe2931hph5kl.apps.googleusercontent.com";
    private static final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    public static GoogleIdToken.Payload verifyGoogleToken(String idTokenString) {
        try {
            if (idTokenString == null || idTokenString.isEmpty()) {
                throw new IllegalArgumentException("Token Google está vazio ou nulo!");
            }

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    jsonFactory
            )
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                return idToken.getPayload();
            } else {
                System.out.println("ID Token inválido");
                return null;
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Erro ao verificar o ID Token do Google", e);
        }
    }
}
