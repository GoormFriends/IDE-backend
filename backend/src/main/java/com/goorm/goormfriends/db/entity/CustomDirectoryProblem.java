package com.goorm.goormfriends.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDirectoryProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="custom_directory_problem_id")
    private Long id;

    @ManyToOne(targetEntity = Problem.class, fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(targetEntity = CustomDirectory.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_directory_id")
    private CustomDirectory customDirectory;

    public CustomDirectoryProblem(CustomDirectory customDirectory, Problem problem) {
        this.problem = problem;
        this.customDirectory = customDirectory;
    }
}
