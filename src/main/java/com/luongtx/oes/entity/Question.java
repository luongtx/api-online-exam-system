package com.luongtx.oes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Entity(name = "question")
@Table(name = "QUESTION")
public class Question {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CONTENT")
    @Lob
    private String content;

    @Column(name = "IS_MULTI_OPTIONS")
    private boolean isMultipleOptions;

    @Column(name = "MOD_DATE")
    private LocalDate modDate;

    @Column(name = "REG_DATE")
    private LocalDate regDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Answer> answers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "EXAM_ID")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    @JsonBackReference
    private Category category;
}
