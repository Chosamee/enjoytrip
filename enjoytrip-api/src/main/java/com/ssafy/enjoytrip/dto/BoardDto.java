package com.ssafy.enjoytrip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private Long articleno;
    private String email;
    private String title;
    private String content;
    private int hit;
    private String createdDate;
}
