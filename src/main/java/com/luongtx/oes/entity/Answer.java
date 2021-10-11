package com.luongtx.oes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "answer")
@Table(name = "ANSWER")
public class Answer {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CONTENT")
    @Lob
    private String content;

    @Column(name = "MOD_DATE")
    private LocalDate modDate;

    @Column(name = "IS_CORRECT")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    @JsonBackReference
    private Question question;
}
