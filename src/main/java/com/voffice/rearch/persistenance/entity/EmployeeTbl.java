package com.voffice.rearch.persistenance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "employee_tbl", catalog = "unity_ol")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeTbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_code")
    String employeeCode;

    @Column(name = "employee_name")
    String employeeName;

    @Column(name = "title")
    String title;

    @Column(name = "rights")
    String rights;

    @Column(name = "accountstatus")
    String accountStatus;

    @Column(name = "division")
    String division;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "designation")
    String designation;

    @Column(name = "email")
    String email;

    @Column(name = "created_on")
    String createdOn;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "gender")
    String gender;

    @Column(name = "marital_status")
    String maritalStatus;

    @Column(name = "employee_role")
    String employeeRole;

    @Column(name = "chat_status")
    String chatStatus;

    @Column(name = "attachment")
    String attachment;

    @Column(name = "original_attachment_name")
    String originalAttachmentName;

    @Column(name = "table_name")
    String tableName;

    @Column(name = "password_status")
    String passwordStatus;

    @Column(name = "login_status")
    String loginStatus;

    @Column(name = "session_id")
    String sessionId;

    @Column(name = "landline_no")
    String landlineNo;

    @Column(name = "mobile_no")
    String mobileNo;

    @Column(name = "alternate_email")
    String alternateEmail;

    @Column(name = "pan")
    String permanentAccountNo;

    @Column(name = "blood_group")
    String bloodGroup;

    @Column(name = "date_of_birth")
    String dateOfBirth;

    @Column(name = "address1")
    String address1;

    @Column(name = "address2")
    String address2;

    @Column(name = "date_of_joining")
    String dateOfJoining;

    @Column(name = "temp_state")
    String tempState;

    @Column(name = "temp_city")
    String tempCity;

    @Column(name = "temp_pin_code")
    String tempPinCode;

    @Column(name = "state")
    String state;

    @Column(name = "city")
    String city;

    @Column(name = "pin_code")
    String pinCode;

    @Column(name = "ctc_per_hour")
    String ctcPerHour;

    @Column(name = "ctc")
    String ctc;

    @Column(name = "join_through")
    String joinThrough;

    @Column(name = "start_date")
    String startDate;

    @Column(name = "end_date")
    String endDate;

    @Column(name = "request_to_authority")
    String requestToAuthority;

    @Column(name = "region")
    String region;

    @Column(name = "addr_state")
    String addrState;

    @Column(name = "addr_city")
    String addrCity;

    @Column(name = "sub_region")
    String subRegion;

    @Column(name = "theme")
    String theme;

    @Column(name = "address1_country")
    String address1Country;

    @Column(name = "address1_globalregion")
    String address1Globalregion;

    @Column(name = "address2_country")
    String address2Country;

    @Column(name = "address2_subregion")
    String address2Subregion;

    @Column(name = "address2_region")
    String address2Region;

    @Column(name = "address1_subregion")
    String address1Subregion;

    @Column(name = "address2_globalregion")
    String address2Globalregion;

    @Column(name = "globalregion")
    String globalregion;

    @Column(name = "address1_region")
    String address1Region;

    @Column(name = "request_to_report")
    String requestToReport;

    @Column(name = "emg_contact_no")
    String emgContactNo;

    @Column(name = "current_emp")
    String currentEmp;

    @Column(name = "partner_type")
    String partnerType;

    @Column(name = "temp_landline_no")
    String tempLandlineNo;

    @Column(name = "temp_mobile_no")
    String tempMobileNo;

    @Column(name = "temp_emg_contact_no")
    String tempEmgContactNo;

    @Column(name = "set_access")
    String setAccess;

    @Column(name = "temp_delete")
    String tempDelete;

    @Column(name = "apple_phoneid")
    String applePhoneid;

    @Column(name = "mobile_login_status")
    String mobileLoginStatus;

    @Column(name = "ctc_year")
    String ctcYear;

    @Column(name = "ctc_month")
    String ctcMonth;

    @Column(name = "rate_per")
    String ratePer;

    @Column(name = "current_emp_rate")
    String currentEmpRate;

    @Column(name = "emp_rate_currency")
    String empRateCurrency;

    @Column(name = "user_device_type")
    String userDeviceType;

    @Column(name = "dept_id")
    String deptId;

    @Column(name = "refe_name1")
    String refeName1;

    @Column(name = "refe_number1")
    String refeNumber1;

    @Column(name = "refe_name2")
    String refeName2;

    @Column(name = "refe_number2")
    String refeNumber2;

    @Column(name = "skype_id")
    String skypeId;

    @Column(name = "modified_on")
    String modifiedOn;

    @Column(name = "modified_by")
    String modifiedBy;

    @Column(name = "milestone_status")
    String milestoneStatus;

    @Column(name = "rework")
    String rework;
}