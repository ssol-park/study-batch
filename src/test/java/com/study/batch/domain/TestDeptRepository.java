package com.study.batch.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
public class TestDeptRepository {

    @Autowired
    private DeptRepository deptRepository;

    @Test
    @Commit
    public void dept01() {
        for (int i = 1; i < 101; i++) {
            Dept dept = Dept.builder()
                    .deptNo(i)
                    .name(String.format("%s%d", "name_", i))
                    .loc(String.format("%s%d", "loc_", i))
                    .build();

            deptRepository.save(dept);
        }
    }
}
