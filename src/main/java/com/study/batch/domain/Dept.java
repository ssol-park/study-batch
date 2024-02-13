package com.study.batch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dept")
public class Dept {

    @Id
    private Integer deptNo;
    private String name;
    private String loc;

    @Builder
    public Dept(Integer deptNo, String name, String loc) {
        this.deptNo = deptNo;
        this.name = name;
        this.loc = loc;
    }
}
