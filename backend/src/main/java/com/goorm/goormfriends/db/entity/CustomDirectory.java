package com.goorm.goormfriends.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class CustomDirectory {
    @Id @GeneratedValue
    @Column(name = "custom_directory_id")
    private Long id;
    private String directory_name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customDirectory")
    private CustomDirectoryProblem customDirectoryProblem;

}
