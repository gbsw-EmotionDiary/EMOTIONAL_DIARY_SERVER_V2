package com.diary.diary.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class DiaryDto {
    private Date date;
    private String title;
    private String content;
    private int emotion;
}
