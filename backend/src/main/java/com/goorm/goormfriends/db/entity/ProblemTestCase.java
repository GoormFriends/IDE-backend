package com.goorm.goormfriends.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class ProblemTestCase {
    @Id @GeneratedValue
    @Column(name = "problom_test_case_id")
    private Long id;
    private String input;
    private String output;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id") //FK
    private Problem problem;


}
