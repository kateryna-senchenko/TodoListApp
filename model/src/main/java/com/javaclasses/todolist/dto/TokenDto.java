package com.javaclasses.todolist.dto;

import com.javaclasses.todolist.tinytypes.TokenId;
import com.javaclasses.todolist.tinytypes.UserId;

/**
 * Token DTO
 */
public class TokenDto {

    private final TokenId tokenId;
    private final UserId userId;

    public TokenDto(TokenId tokenId, UserId userId) {
        this.tokenId = tokenId;
        this.userId = userId;
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

        TokenDto tokenDto = (TokenDto) o;

        if (!tokenId.equals(tokenDto.tokenId)) return false;
        return userId.equals(tokenDto.userId);

    }

    @Override
    public int hashCode() {
        int result = tokenId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}
