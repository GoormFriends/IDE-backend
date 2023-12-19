package com.goorm.goormfriends.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDirectory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_directory_id")
    private Long id;

    private String directory_name;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customDirectory", cascade = CascadeType.ALL)
    private List<CustomDirectoryProblem> customDirectoryProblems = new ArrayList<>();

    public CustomDirectory(String title, Long userId) {
        this.directory_name = title;
        this.user = new User();
        user.setId(userId);
    }
}
