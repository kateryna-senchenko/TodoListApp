package com.javaclasses.todolist.entities;

import com.javaclasses.todolist.tinytypes.TokenId;
import com.javaclasses.todolist.tinytypes.UserId;

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

        if (!tokenId.equals(token.tokenId)) return false;
        return userId.equals(token.userId);

    }

    @Override
    public int hashCode() {
        int result = tokenId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}
