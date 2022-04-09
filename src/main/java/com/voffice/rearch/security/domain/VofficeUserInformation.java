package com.voffice.rearch.security.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VofficeUserInformation {
    private String username;
    private String password;
    private String authority;
    private String empCode;
    private String empDesignation;
}