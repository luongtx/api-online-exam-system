package com.luongtx.oes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "EXAM_ID")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "CATALOG_ID")
    // @JsonBackReference
    private Catalog catalog;

    public List<Long> getCorrectAnswers() {
        return answers.stream()
                .filter(Answer::isCorrect).map(Answer::getId)
                .collect(Collectors.toList());
    }

    public void setAnswers(List<Answer> answers) {
        if (this.answers == null) {
            this.answers = new ArrayList<>();
        }
        this.answers.clear();
        this.answers.addAll(answers);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", answers=" + answers +
                ", examId=" + (exam != null ? exam.getId() : null) +
                ", catalogId=" + (catalog != null ? catalog.getId() : null) +
                '}';
    }
}
