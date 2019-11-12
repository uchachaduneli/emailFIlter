package com.email.filter.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "filter", schema = "emailfilter")
public class Filter {
    private Integer id;
    private String desc;
    private Timestamp createDate;
    private Timestamp updateDate;
    private FilterType type;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`desc`")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "create_date", insertable = false, updatable = false)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "update_date", insertable = false, updatable = false)
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @JoinColumn(name = "type_id")
    @OneToOne
    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }
}
