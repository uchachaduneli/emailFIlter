package com.email.filter.model;

import javax.persistence.*;

@Entity
@Table(name = "filter_type", schema = "emailfilter")
public class FilterType {
    private Integer Id;
    private String name;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
