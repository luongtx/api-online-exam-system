package com.luongtx.oes.web;

import java.util.HashMap;
import java.util.Map;

import com.luongtx.oes.constants.PageConstants;
import com.luongtx.oes.constants.RoleConstants;
import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/questions")
@Log4j2
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/all")
    Map<String, Object> getAll(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false, defaultValue = "1000") Integer size,
                               @RequestParam(value = "search", required = false, defaultValue = "") String searchKey,
                               @RequestParam(value = "catalog", required = false) Long catalogId,
                               @RequestParam(value = "exam", required = false) Long examId) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDTO> questionDTOS;
        if (examId == null && catalogId == null) {
            questionDTOS = questionService.findAll(pageable, searchKey);
        } else if (catalogId != null) {
            questionDTOS = questionService.findAllByCatalog(pageable, searchKey, catalogId);
        } else {
            questionDTOS = questionService.findAllByExam(pageable, searchKey, examId);
        }
        log.debug(questionDTOS.getContent());
        response.put(PageConstants.DATA, questionDTOS.getContent());
        response.put(PageConstants.TOTAL_PAGE, questionDTOS.getTotalPages());
        return response;
    }

    @GetMapping("/exclude-catalog")
    Map<String, Object> getAllExcludedCatalog(@RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "size", required = false) Integer size,
                                              @RequestParam(value = "search", required = false, defaultValue = "") String searchKey,
                                              @RequestParam(value = "catalog") Long catalogId) {

        Map<String, Object> response = new HashMap<>();
        if (page == null || size == null) {
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            Page<QuestionDTO> questionDTOS = questionService.findAllExcludeCatalog(pageable, searchKey, catalogId);
            log.debug("[getAllExcludedCatalog] number of questions: {}", questionDTOS.getContent().size());
            response.put(PageConstants.DATA, questionDTOS);
            response.put(PageConstants.TOTAL_PAGE, 1);
            return response;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDTO> questionDTOS = questionService.findAllExcludeCatalog(pageable, searchKey, catalogId);
        log.debug(questionDTOS.getContent());
        response.put(PageConstants.DATA, questionDTOS.getContent());
        response.put(PageConstants.TOTAL_PAGE, questionDTOS.getTotalPages());
        return response;
    }

    @GetMapping("/exclude-exam")
    Map<String, Object> getAllExcludedExam(@RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size,
                                           @RequestParam(value = "search", required = false, defaultValue = "") String searchKey,
                                           @RequestParam(value = "exam") Long examId) {

        Map<String, Object> response = new HashMap<>();
        if (page == null || size == null) {
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            Page<QuestionDTO> questionDTOS = questionService.findAllExcludeExam(pageable, searchKey, examId);
            log.debug("[getAllExcludedExam] number of questions: {}", questionDTOS.getContent().size());
            response.put(PageConstants.DATA, questionDTOS);
            response.put(PageConstants.TOTAL_PAGE, 1);
            return response;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDTO> questionDTOS = questionService.findAllExcludeExam(pageable, searchKey, examId);
        log.debug(questionDTOS.getContent());
        response.put(PageConstants.DATA, questionDTOS.getContent());
        response.put(PageConstants.TOTAL_PAGE, questionDTOS.getTotalPages());
        return response;
    }

    @PostMapping({"/save", "/update"})
    public void saveQuestion(@RequestBody QuestionDTO questionDTO) {
        log.debug(questionDTO);
        questionService.save(questionDTO);
    }

    @DeleteMapping(value = "/{id}/delete")
    @Secured(RoleConstants.ROLE_ADMIN)
    public void deleteQuestion(@PathVariable("id") Long questionId,
                               @RequestParam(value = "catalog", required = false) Long catalogId,
                               @RequestParam(value = "exam", required = false) Long examId) {
        questionService.deleteQuestion(questionId, catalogId, examId);
    }
}
