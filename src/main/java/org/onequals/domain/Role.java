package org.onequals.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, EMPLOYER, SEEKER;

    @Override
    public String getAuthority() {
        return name();
    }
}