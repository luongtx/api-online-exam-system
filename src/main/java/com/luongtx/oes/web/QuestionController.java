package com.luongtx.oes.web;

import com.luongtx.oes.constants.PageConstants;
import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = {"http://localhost:4200"})
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("")
    Map<String, Object> getAllExcludedCatalog(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "size", required = false) Integer size,
                                               @RequestParam(value = "search", required = false, defaultValue = "") String searchKey,
                                               @RequestParam(value = "catalog") Long catalogId
                                               ) {

        Map<String, Object> response = new HashMap<>();
        if (page == null || size == null) {
            Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
            response.put(PageConstants.DATA, questionService.findAllExcluded(pageable, searchKey, catalogId));
            return response;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDTO> questionDTOS = questionService.findAllExcluded(pageable, searchKey, catalogId);
        response.put(PageConstants.DATA, questionDTOS.getContent());
        response.put(PageConstants.TOTAL_PAGE, questionDTOS.getTotalPages());
        return response;
    }
}
