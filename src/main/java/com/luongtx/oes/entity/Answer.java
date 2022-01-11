package com.luongtx.oes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "answer")
@Table(name = "ANSWER")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTENT")
    @Lob
    private String content;

    @Column(name = "IS_CORRECT")
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    // @JsonBackReference
    private Question question;

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", isCorrect=" + correct +
                '}';
    }
}
