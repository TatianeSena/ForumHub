package com.forumhub.api;

import com.forumhub.api.dto.TopicRequest;
import com.forumhub.api.dto.TopicResponse;
import com.forumhub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicController {

    private final TopicService service;
    public TopicController(TopicService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<TopicResponse> create(@RequestBody @Valid TopicRequest dto) {
        var created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public Page<TopicResponse> list(
            @RequestParam(required = false) String course,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false, defaultValue = "false") boolean top10,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {

        if (top10) return service.listTop10Asc();
        if (course != null) return service.listByCourseAndYear(course, year, pageable);
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public TopicResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public TopicResponse update(@PathVariable Long id, @RequestBody @Valid TopicRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

