package com.javaclasses.todolistapp.entities;

import com.javaclasses.todolistapp.tinytypes.TokenId;
import com.javaclasses.todolistapp.tinytypes.UserId;

/**
 * Token entity
 */
public class Token {

    private TokenId tokenId;
    private final UserId userId;

    public Token(UserId userId) {
        this.userId = userId;
    }

    public void setTokenId(TokenId tokenId) {
        this.tokenId = tokenId;
    }

    public TokenId getTokenId() {
        return tokenId;
    }

    public UserId getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        return tokenId.equals(token.tokenId) && userId.equals(token.userId);

    }

    @Override
    public int hashCode() {
        int result = tokenId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}
