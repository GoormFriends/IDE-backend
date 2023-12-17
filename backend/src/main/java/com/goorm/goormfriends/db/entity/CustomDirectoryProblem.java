package com.goorm.goormfriends.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDirectoryProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="custom_directory_problem_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "custom_directory_id")
    private CustomDirectory customDirectory;
}
