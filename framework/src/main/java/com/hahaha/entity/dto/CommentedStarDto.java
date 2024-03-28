package com.hahaha.entity.dto;

import lombok.Data;

@Data
public class CommentedStarDto {
    int starNum;
    Long userId;
    Long videoId;
}
