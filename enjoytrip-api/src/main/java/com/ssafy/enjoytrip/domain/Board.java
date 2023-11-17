package com.ssafy.enjoytrip.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long articleNo;
    private String userEmail;
    private String title;
    private String content;
    private int hit;
    @CreatedDate
    private String createdDate;
}
