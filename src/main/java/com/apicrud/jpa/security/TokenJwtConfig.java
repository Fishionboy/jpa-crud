package com.apicrud.jpa.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public class TokenJwtConfig {

    public static final SecretKey SECRET_KEY =
        Keys.hmacShaKeyFor("my-secret-key-my-secret-key-123456".getBytes());
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

}
