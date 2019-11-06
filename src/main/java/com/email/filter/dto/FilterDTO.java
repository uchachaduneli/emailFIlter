package com.email.filter.dto;

import com.email.filter.misc.JsonDateSerializeSupport;
import com.email.filter.model.Filter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterDTO {

    private Integer id;
    private String desc;
    @JsonSerialize(using = JsonDateSerializeSupport.class)
    private Date createDate;
    @JsonSerialize(using = JsonDateSerializeSupport.class)
    private Date updateDate;
    private FilterTypeDTO type;
    private Integer typeId;

    public static FilterDTO parse(Filter record) {
        if (record != null) {
            FilterDTO dto = new FilterDTO();
            dto.setId(record.getId());
            dto.setDesc(record.getDesc());
            dto.setCreateDate(record.getCreateDate());
            dto.setUpdateDate(record.getUpdateDate());
            if (record.getType() != null) {
                dto.setType(FilterTypeDTO.parse(record.getType()));
                dto.setTypeId(record.getType().getId());
            }
            return dto;
        } else return null;
    }

    public static List<FilterDTO> parseToList(List<Filter> records) {
        ArrayList<FilterDTO> list = new ArrayList<FilterDTO>();
        for (Filter record : records) {
            list.add(FilterDTO.parse(record));
        }
        return list;
    }

    public FilterTypeDTO getType() {
        return type;
    }

    public void setType(FilterTypeDTO type) {
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
