package com.luongtx.oes.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity(name = "user_exam")
@Table(name = "USER_EXAM")
public class UserExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NonNull
    @Column(name = "USER_ID")
    private Long userId;

    @NonNull
    @Column(name = "EXAM_ID")
    private Long examId;

    @Column(name = "FINISHED_DATE")
    private LocalDateTime finishedDate;

    @NonNull
    @Column(name = "EXAM_SCORE")
    private Integer score;

    @NonNull
    @Column(name = "EXAM_STATUS")
    private Boolean status;
}
