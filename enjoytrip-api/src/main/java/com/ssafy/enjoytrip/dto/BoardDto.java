package com.ssafy.enjoytrip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private String userEmail;
    private String title;
    private String content;
    private int hit;
}
