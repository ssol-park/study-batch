package com.study.batch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "dept")
public class Dept2 {

    @Id
    private Integer deptNo;
    private String name;
    private String loc;

    @Builder
    public Dept2(Integer deptNo, String name, String loc) {
        this.deptNo = deptNo;
        this.name = name;
        this.loc = loc;
    }
}
