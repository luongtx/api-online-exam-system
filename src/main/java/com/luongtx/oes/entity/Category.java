package com.luongtx.oes.entity;

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
@Entity(name = "category")
@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MOD_DATE")
    private LocalDate modDate;

    @Column(name = "REG_DATE")
    private LocalDate regDate;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Question> questions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "CATEGORY_PARENT_ID", foreignKey = @ForeignKey(name = "CATEGORY_ID_FK"))
    Category category;
}
