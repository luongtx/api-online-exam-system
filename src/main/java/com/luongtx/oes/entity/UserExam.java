package com.luongtx.oes.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "user_exam")
@Table(name = "USER_EXAM")
public class UserExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EXAM_ID")
    private Long examId;

    @Column(name = "FINISHED_DATE")
    private LocalDateTime finishedDate;

    @Column(name = "EXAM_SCORE")
    private Integer score;

    @Column(name = "EXAM_STATUS")
    private Boolean status;
}
