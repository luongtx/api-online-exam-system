package com.luongtx.oes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "question")
@Table(name = "QUESTION")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTENT")
    @Lob
    private String content;

//    @Column(name = "IS_MULTI_OPTIONS")
//    private boolean isMultipleOptions;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Answer> answers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "EXAM_ID")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "CATALOG_ID")
    @JsonBackReference
    private Catalog catalog;

    public List<Long> getCorrectAnswers() {
        return answers.stream()
                .filter(Answer::isCorrect).map(Answer::getId)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", answers=" + answers +
                ", examId=" + exam.getId() +
                '}';
    }
}
