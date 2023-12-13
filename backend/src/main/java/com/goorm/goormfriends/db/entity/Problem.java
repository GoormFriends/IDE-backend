package com.goorm.goormfriends.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Problem {
    @Column(name="problom_id")
    private Long id;
    private String title;
    private String content;
    private int level;

    @OneToMany(mappedBy = "problem")
    private ProblemTestCase problemTestCase;

    @OneToMany(mappedBy = "problem")
    private CustomDirectoryProblem customDirectoryProblem;

    @OneToMany(mappedBy = "problem")
    private Ide ide;

}
