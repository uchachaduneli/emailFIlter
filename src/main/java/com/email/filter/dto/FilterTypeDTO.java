package com.email.filter.dto;

import com.email.filter.model.FilterType;

import java.util.ArrayList;
import java.util.List;

public class FilterTypeDTO {

    private Integer id;
    private String name;

    public static int IP_FILTER = 2;

    public static FilterTypeDTO parse(FilterType record) {
        FilterTypeDTO dto = new FilterTypeDTO();
        dto.setId(record.getId());
        dto.setName(record.getName());
        return dto;
    }


    public static List<FilterTypeDTO> parseToList(List<FilterType> records) {
        ArrayList<FilterTypeDTO> list = new ArrayList<FilterTypeDTO>();
        for (FilterType record : records) {
            list.add(FilterTypeDTO.parse(record));
        }
        return list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
