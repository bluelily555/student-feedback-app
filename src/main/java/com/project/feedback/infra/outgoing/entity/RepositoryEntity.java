package com.project.feedback.infra.outgoing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repository")
public class RepositoryEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String name;

    private String address;

    public void setAddress(String address) {
        this.address = address;
    }

    public void update(RepositoryEntity update) {
        this.name = update.getName();
        this.address = update.getAddress();
    }

    public boolean equalsOwner(UserEntity user) {
        return this.user.equals(user);
    }
}
