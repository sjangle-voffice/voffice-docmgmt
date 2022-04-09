package com.voffice.rearch.security.service;

import com.voffice.rearch.security.domain.VofficeRole;
import com.voffice.rearch.security.domain.VofficeUserDetails;
import com.voffice.rearch.security.domain.VofficeUserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<VofficeUserInformation> vofficeUserInformationList = jdbcTemplate.query(
				"select employee.username, employee.password, employee.employee_code, employee.designation, \n"
						+ " userRole.user_access_role_id \n"
						+ " from employee_tbl employee , master_user_role userRole \n"
						+ " where employee.username = ? \n"
						+ " and employee.employee_role = userRole.user_role_id",
				new Object[]{username}, new RowMapper<VofficeUserInformation>() {
					@Override
					public VofficeUserInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
						VofficeUserInformation userInfo = new VofficeUserInformation();
						userInfo.setUsername(rs.getString(1));
						userInfo.setPassword(rs.getString(2));
						userInfo.setEmpCode(rs.getString(3));
						userInfo.setEmpDesignation(rs.getString(4));
						userInfo.setAuthority(rs.getString(5));
						return userInfo;
					}
				});

		if ( vofficeUserInformationList.size() > 0 ) {

			System.out.println("loadUserByUsername : userAccessRolesIds : "
					+ vofficeUserInformationList.get(0).getAuthority());

			List<String> userRoleIdList = new ArrayList<>();
			if (vofficeUserInformationList.get(0).getAuthority() != null) {
				List<String> userAccessRoleAutoIdsList = Arrays.asList(vofficeUserInformationList.get(0).getAuthority().split(","));

				List<String> userAllowedScreenActions = null;
				for (String userAccessRoleId : userAccessRoleAutoIdsList) {

					userAllowedScreenActions = new ArrayList<>();
					System.out.println("loadUserByUsername : userAccessRoleId : " + userAccessRoleId);

					String userRoleId = null;
					try {
						userRoleId =
								jdbcTemplate.queryForObject(
										"select user_roles_id from user_role_tbl where user_role_auto = ?",
										new Object[] { userAccessRoleId }, String.class);
					}
					catch (EmptyResultDataAccessException exp) {
						System.out.println("No user role found for role id.");
					}

					if (userRoleId != null) {
						System.out.println("loadUserByUsername : userRolesId : " + userRoleId);
						populateUserRoleIdList(userRoleIdList, userRoleId, userAllowedScreenActions);
						System.out.println("loadUserByUsername : userRoleIdList : " + userRoleIdList);
					}
				}

				List<GrantedAuthority> authorities = new ArrayList<>();
				userRoleIdList.stream().forEach(s ->
						authorities.add(new VofficeRole(s)));

				VofficeUserDetails user = buildUserForAuthentication(
						vofficeUserInformationList.get(0).getUsername(),
						vofficeUserInformationList.get(0).getPassword(),
						1L,
						authorities,
						userAllowedScreenActions,
						vofficeUserInformationList.get(0).getEmpCode(),
						vofficeUserInformationList.get(0).getEmpDesignation(),
						vofficeUserInformationList.get(0).getAuthority());

				return user;
			}
		}
		return null;
	}

	private void populateUserRoleIdList(List<String> userRoleIdList,
										String userRoleId,
										List<String> userAllowedScreenActions) {

		if (userRoleId != null && userRoleId.contains("@")) {

			String screenName = userRoleId.substring(0 , userRoleId.indexOf("@"));
			System.out.println("screenName :" + screenName);

			String remainUserRoleStr = userRoleId.substring(userRoleId.indexOf("@") + 1);
			System.out.println("remaining :" + remainUserRoleStr);

			List<String> screenActions = Arrays.asList(remainUserRoleStr.split("@"));
			screenActions.stream().forEach(
					screenAction -> userRoleIdList.add(
							"ROLE" + "_" + screenName.toUpperCase() + "_" + screenAction.toUpperCase()));

			screenActions.stream().forEach(
					screenAction -> userAllowedScreenActions.add(
							screenName.toUpperCase() + "_" + screenAction.toUpperCase()));

		}
	}

	private VofficeUserDetails buildUserForAuthentication(
			String userName, String password,Long userId,
			List<GrantedAuthority> authorities,
			List<String> userAllowedScreenActions,
			String empCode, String empDesignation,
			String userAccessRoleId) {

		return new VofficeUserDetails(userName, password, userId,
				true, true, true, true,
				authorities, userAllowedScreenActions,
				empCode, empDesignation, userAccessRoleId);
	}
}