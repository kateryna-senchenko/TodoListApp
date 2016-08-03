package com.javaclasses.todolist.storage;

import com.javaclasses.todolist.entities.Token;
import com.javaclasses.todolist.tinytypes.TokenId;

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
