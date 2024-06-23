package com.adkp.fuexchange.response;

import com.adkp.fuexchange.pojo.Roles;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StaffInforResponse {

    private Roles roleId;

    private String staffName;



    private String gender;
    private String identityCard;

    private String address;
    private String phoneNumber;

    private LocalDate dob;

    public StaffInforResponse(Roles roleId, String staffName, String gender, String identityCard, String address, String phoneNumber, LocalDate dob) {
        this.roleId = roleId;
        this.staffName = staffName;
        this.gender = gender;
        this.identityCard = identityCard;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
    }
}
