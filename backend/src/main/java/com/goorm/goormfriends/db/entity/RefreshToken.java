package com.goorm.goormfriends.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="refresh_token")
@Getter
@ToString
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity{
    @Id
    @Column(name = "refreshtoken_key")
    private String key;

    @Column(name = "refreshtoken_value")
    private String value;

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
