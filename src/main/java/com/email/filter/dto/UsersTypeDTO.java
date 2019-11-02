package com.email.filter.dto;

import com.email.filter.model.UserTypes;

import java.util.ArrayList;
import java.util.List;

public class UsersTypeDTO {

    private Integer userTypeId;
    private String userTypeName;


    public static UsersTypeDTO parse(UserTypes record) {
        UsersTypeDTO dto = new UsersTypeDTO();
        dto.setUserTypeId(record.getUserTypeId());
        dto.setUserTypeName(record.getUserTypeName());
        return dto;
    }


    public static List<UsersTypeDTO> parseToList(List<UserTypes> records) {
        ArrayList<UsersTypeDTO> list = new ArrayList<UsersTypeDTO>();
        for (UserTypes record : records) {
            list.add(UsersTypeDTO.parse(record));
        }
        return list;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }
}
