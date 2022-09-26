package com.luongtx.oes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CatalogDTO {
    private Long id;
    private Long parentId;
    private String name;
    private Long numberOfQuestions;
}
