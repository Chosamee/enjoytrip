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
public class Board {
    @Id
    @GeneratedValue(generator = "BOARD_GENERATOR")
    @Column(name = "articleno")
    private Long articleno;
    private String email;
    private String title;
    private String content;
    private int hit;
    @CreatedDate
    private String createdDate;
}
