package com.voffice.rearch.security.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Successful logged-in User Details along with Authorities.
 * @author Sagar Jangle.
 */
@Getter
@Setter
public class VofficeUserDetails extends User {

    Long userId;
    List<String> userAllowedScreenActions = new ArrayList<>();
    String empCode;
    String empDesignation;
    String userAccessRoleId;

    public VofficeUserDetails(String username, String password, Long userId,
                              Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public VofficeUserDetails(String username, String password, Long userId, boolean enabled,
                              boolean accountNonExpired,
                              boolean credentialsNonExpired, boolean accountNonLocked,
                              Collection<? extends GrantedAuthority> authorities,
                              List<String> userAllowedScreenActions, String empCode,
                              String empDesignation, String userAccessRoleId) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked,
            authorities);

        this.userId = userId;
        this.userAllowedScreenActions = userAllowedScreenActions;
        this.empCode = empCode;
        this.empDesignation = empDesignation;
        this.userAccessRoleId = userAccessRoleId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  return true; }

    @Override
    public boolean isEnabled() {
        return true;
    }
}