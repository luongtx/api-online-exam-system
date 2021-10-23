package com.luongtx.oes.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamResultDTO {
    private Long examId;
    private String title;
    private String description;
    private Integer score;
    private Integer passingScore;
    private Boolean status;
    private LocalDateTime finishedAt;
    private String bannerImage;
}
