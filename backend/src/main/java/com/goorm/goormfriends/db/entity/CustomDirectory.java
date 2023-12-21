package com.goorm.goormfriends.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.goorm.goormfriends.api.dto.request.UpdateDirectoryRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="custom_directory")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDirectory extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_directory_id")
    private Long id;

    private String directory_name;

    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customDirectory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomDirectoryProblem> customDirectoryProblems = new ArrayList<>();

    public CustomDirectory(String title, User user) {
        this.directory_name = title;
        this.user = user;
    }

    public void updateCustomDirectory(String newDirectoryTitle) {
        this.directory_name = newDirectoryTitle;
    }
}
