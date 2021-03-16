package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
    private UUID token;

    public AuthToken(UUID uuid) {
        this.token = uuid;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return token.equals(authToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}