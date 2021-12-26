package com.luongtx.oes.web;

import com.luongtx.oes.constants.PageConstants;
import com.luongtx.oes.constants.RoleConstants;
import com.luongtx.oes.dto.CatalogDTO;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.service.CatalogService;
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
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @GetMapping("")
    Map<String, Object> getAll(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size,
                               @RequestParam(value = "search", required = false, defaultValue = "") String searchKey,
                               @RequestParam(value = "sort", required = false, defaultValue = "id") String sortKey
    ) {
        Map<String, Object> response = new HashMap<>();
        if (page == null || size == null) {
            response.put(PageConstants.DATA, catalogService.findAll());
            return response;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortKey));
        Page<CatalogDTO> categoryDTOPage = catalogService.findAll(pageable, searchKey);
        response.put(PageConstants.DATA, categoryDTOPage.getContent());
        response.put(PageConstants.TOTAL_PAGE, categoryDTOPage.getTotalPages());
        return response;
    }

    @GetMapping("/{id}")
    CatalogDTO getById(@PathVariable("id") Long id) {
        return catalogService.findById(id);
    }

    @GetMapping("/{id}/questions")
    Map<String, Object> getAllQuestions(
            @PathVariable("id") Long catalogId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(value = "search", required = false, defaultValue = "") String searchKey,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sortKey) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortKey));
        Page<Question> questionPage = catalogService.findAllQuestions(catalogId, pageable, searchKey);
        response.put(PageConstants.DATA, questionPage.getContent());
        response.put(PageConstants.TOTAL_PAGE, questionPage.getTotalPages());
        return response;
    }

    @DeleteMapping("/delete/{id}")
    @Secured(RoleConstants.ROLE_ADMIN)
    void delete(@PathVariable("id") Long id,
                @RequestParam(value = "cascade", defaultValue = "false") boolean cascade) {
        catalogService.delete(id, cascade);
    }

    @PostMapping("/save")
    @Secured(RoleConstants.ROLE_ADMIN)
    void saveGeneralInfo(@RequestBody CatalogDTO catalogDTO) {
        catalogService.save(catalogDTO);
    }

    @PutMapping("/{id}/save/question/{questionId}")
    @Secured(RoleConstants.ROLE_ADMIN)
    void saveQuestion(@PathVariable("id") Long catalogId, @PathVariable("questionId") Long questionId) {
        catalogService.saveQuestion(catalogId, questionId);
    }
    
    @PostMapping("/{id}/new/question/")
    @Secured(RoleConstants.ROLE_ADMIN)
    void saveNewQuestion(@PathVariable("id") Long catalogId, @RequestBody Question question) {
        catalogService.saveQuestion(catalogId, question);
    }

    @PostMapping("/{id}/save/questions")
    @Secured(RoleConstants.ROLE_ADMIN)
    void saveQuestions(@PathVariable("id") Long catalogId, @RequestBody List<Question> questionList) {
        catalogService.saveQuestions(catalogId, questionList);
    }

    @DeleteMapping("/remove/question/{questionId}")
    void removeQuestion(@PathVariable("questionId") Long questionId) {
        catalogService.removeQuestion(questionId);
    }

}
