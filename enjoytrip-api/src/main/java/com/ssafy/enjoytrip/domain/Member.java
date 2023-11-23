package com.ssafy.enjoytrip.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String token;
    @CreatedDate
    private String createdDate;
}
