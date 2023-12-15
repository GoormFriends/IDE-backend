package com.goorm.goormfriends.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Problem {
    @Id @GeneratedValue
    @Column(name="problom_id")
    private Long id;
    private String title;
    private String content;
    private int level;

    @OneToMany(mappedBy = "problem")
    private List<ProblemTestCase> problemTestCases = new ArrayList<>();

    @OneToMany(mappedBy = "problem")
    private List<CustomDirectoryProblem> customDirectoryProblems = new ArrayList<>();

    @OneToMany(mappedBy = "problem")
    private List<Ide> ides = new ArrayList<>();

}
