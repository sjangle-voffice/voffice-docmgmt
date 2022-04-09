package com.voffice.rearch.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * The class to store the roles related to User.
 * @author Sagar Jangle.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VofficeRole implements GrantedAuthority {

    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}