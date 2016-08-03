package com.javaclasses.todolistapp.storage;

import com.javaclasses.todolistapp.entities.Token;
import com.javaclasses.todolistapp.tinytypes.TokenId;

import java.util.UUID;

/**
 * Implementation of the Repository for token Repository
 */
public class TokenRepositoryImpl extends InMemoryRepository<TokenId, Token>{

    private static Repository<TokenId, Token> tokenRepository = new TokenRepositoryImpl();

    private TokenRepositoryImpl() {}

    public static Repository<TokenId, Token> getInstance() {
        return tokenRepository;
    }

    @Override
    public TokenId generateId() {
        return new TokenId(UUID.randomUUID());
    }
}
