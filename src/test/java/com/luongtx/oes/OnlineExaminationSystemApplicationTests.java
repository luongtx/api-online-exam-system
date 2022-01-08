package com.luongtx.oes;

import com.luongtx.oes.entity.Question;
import com.luongtx.oes.repository.QuestionRepo;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
class OnlineExaminationSystemApplicationTests {

    @Autowired
    private QuestionRepo questionRepo;

    @Test
    void contextLoads() {
    }

    @Test
    void findQuestionExcept() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        String searchTerm = "";
        Long catalogId = 1L;
        Page<Question> questions = questionRepo.findAllExceptCatalog(pageable, searchTerm, catalogId);
        log.debug(questions.getContent());
        int expected = 2;
        Assertions.assertEquals(expected, questions.getContent().size());
    }

    @Test
    void findQuestionExcept2() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        String searchTerm = "";
        Long catalogId = 2L;
        Page<Question> questions = questionRepo.findAllExceptCatalog(pageable, searchTerm, catalogId);
        log.debug(questions.getContent());
        int expected = 4;
        Assertions.assertEquals(expected, questions.getContent().size());
    }

}
