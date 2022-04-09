package com.voffice.rearch.ms.docmgmt.controller.login;

import com.voffice.rearch.security.domain.VofficeUserDetails;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This login controller will be used to Login to Document Management App.
 * - Successful Login : Returns UserName and Roles Array.
 *      Roles: Array(11)
 *          0: "ROLE_HOME_ADDTODO"
 *          1: "ROLE_HOME_DMSCOLLABORATIONLISTFORLIBRARY"
 *          2: "ROLE_HOME_GETCOUNTRYMASTER"
 *      UserName: "Deepakmore"
 * - UnSuccessful Login : Returns Error message with error code.
 * @author Sagar Jangle.
 * @version 1.0
 * @since 1st Feb 2022
 **/
@CrossOrigin
@RestController
public class LoginPageController {

    private static final Logger LOG = Logger.getLogger(LoginPageController.class.getName());

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        VofficeUserDetails userDetails =
                (VofficeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        jsonObject.put("UserName", userDetails.getUsername());
        jsonObject.put("Roles", userDetails.getAuthorities().stream().map(
                        auth -> auth.getAuthority()).collect(Collectors.toList()));
        jsonObject.put("UserActions", userDetails.getUserAllowedScreenActions());
        jsonObject.put("EmpCode", userDetails.getEmpCode());
        jsonObject.put("EmpDesignation", userDetails.getEmpDesignation());
        jsonObject.put("RoleId", userDetails.getUserAccessRoleId());

        return ResponseEntity.ok().body(jsonObject.toString());
    }
}