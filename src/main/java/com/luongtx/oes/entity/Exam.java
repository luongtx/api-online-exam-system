package com.luongtx.oes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "exam")
@Table(name = "EXAM")
public class Exam {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "EXAM_CODE")
    private String examCode;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "DURATION")
    private int duration;

    @Column(name = "PASSING_SCORE")
    private int passingScore;

    @Column(name = "REG_DATE")
    private LocalDate regDate;

    @Column(name = "MOD_DATE")
    private LocalDate modDate;

    @Column(name = "BANNER_IMAGE")
    private String bannerImage;

    @Transient
    private int numberOfQuestions;

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();
}
