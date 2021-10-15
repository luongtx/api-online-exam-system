package com.luongtx.oes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExamResultDTO {
    private Integer score;
    private boolean status;
}
