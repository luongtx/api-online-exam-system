package com.luongtx.oes.web;

import com.luongtx.oes.constants.PageConstants;
import com.luongtx.oes.constants.RoleConstants;
import com.luongtx.oes.dto.CategoryDTO;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catalogues")
@CrossOrigin(origins = {"http://localhost:4200"})
public class CatalogueController {

    @Autowired
    CatalogueService catalogueService;

    @GetMapping("")
    Map<String, Object> getAll(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size,
                               @RequestParam(value = "search", required = false, defaultValue = "") String searchKey,
                               @RequestParam(value = "sort", required = false, defaultValue = "id") String sortKey
    ) {
        Map<String, Object> response = new HashMap<>();
        if (page == null || size == null) {
            response.put(PageConstants.DATA, catalogueService.findAll());
            return response;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortKey));
        Page<CategoryDTO> categoryDTOPage = catalogueService.findAll(pageable, searchKey);
        response.put(PageConstants.DATA, categoryDTOPage.getContent());
        response.put(PageConstants.TOTAL_PAGE, categoryDTOPage.getTotalPages());
        return response;
    }

    @GetMapping("/{id}")
    CategoryDTO getById(@PathVariable("id") Long id) {
        return catalogueService.findById(id);
    }

    @GetMapping("/{id}/questions")
    Map<String, Object> getAllQuestions(
            @PathVariable("id") Long categoryId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(value = "search", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sortKey) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortKey));
        Page<Question> questionPage = catalogueService.findAllQuestions(categoryId, pageable, searchKey);
        response.put(PageConstants.DATA, questionPage.getContent());
        response.put(PageConstants.TOTAL_PAGE, questionPage.getTotalPages());
        return response;
    }

    @DeleteMapping("/delete/{id}")
    @Secured(RoleConstants.ROLE_ADMIN)
    void delete(@PathVariable("id") Long id,
                @RequestParam(value = "cascade", defaultValue = "false") boolean cascade) {
        catalogueService.delete(id, cascade);
    }

    @PostMapping("/save")
    @Secured(RoleConstants.ROLE_ADMIN)
    void saveGeneralInfo(@RequestBody CategoryDTO categoryDTO) {
        catalogueService.save(categoryDTO);
    }

    @PostMapping("/{id}/save/question/{questionId}")
    @Secured(RoleConstants.ROLE_ADMIN)
    void saveQuestion(@PathVariable("id") Long categoryId, @PathVariable("questionId") Long questionId) {
        catalogueService.saveQuestion(categoryId, questionId);
    }

    @PostMapping("/{id}/save/questions")
    @Secured(RoleConstants.ROLE_ADMIN)
    void saveQuestions(@PathVariable("id") Long categoryId, @RequestBody List<Question> questionList) {
        catalogueService.saveQuestions(categoryId, questionList);
    }

    @PostMapping("/remove/question/{questionId}")
    void removeQuestion(@PathVariable("questionId") Long questionId) {
        catalogueService.removeQuestion(questionId);
    }

}
