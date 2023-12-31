package com.goorm.goormfriends.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Ide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ide_id")
    private Long id;

    private State state;

    @Column(length = 1000)
    private String usercode;

    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public void setUser(User user){
        this.user = user;
        if (user != null ) {
            user.getIdes().add(this);
        }
    }

    public void setProblem(Problem problem){
        this.problem = problem;
        if (problem != null) {
            problem.getIdes().add(this);
        }
    }


}
