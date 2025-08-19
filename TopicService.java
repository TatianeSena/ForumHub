package com.forumhub.service;

import com.forumhub.api.dto.TopicRequest;
import com.forumhub.api.dto.TopicResponse;
import com.forumhub.domain.Topic;
import com.forumhub.domain.TopicRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;

@Service
public class TopicService {
    private final TopicRepository repo;

    public TopicService(TopicRepository repo) { this.repo = repo; }

    @Transactional
    public TopicResponse create(TopicRequest dto) {
        if (repo.existsByTitleAndMessage(dto.title(), dto.message()))
            throw new IllegalArgumentException("Tópico duplicado (título + mensagem).");

        var entity = Topic.builder()
                .title(dto.title())
                .message(dto.message())
                .author(dto.author())
                .course(dto.course())
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .build();

        var saved = repo.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<TopicResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<TopicResponse> listTop10Asc() {
        var pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());
        return repo.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<TopicResponse> listByCourseAndYear(String course, Integer year, Pageable pageable) {
        if (year == null)
            return repo.findByCourseIgnoreCaseOrderByCreatedAtAsc(course, pageable).map(this::toResponse);

        var start = LocalDate.of(year, 1, 1).atStartOfDay();
        var end = LocalDate.of(year, 12, 31).atTime(23,59,59);
        return repo.findByCourseIgnoreCaseAndCreatedAtBetweenOrderByCreatedAtAsc(course, start, end, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public TopicResponse get(Long id) {
        var topic = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));
        return toResponse(topic);
    }

    @Transactional
    public TopicResponse update(Long id, TopicRequest dto) {
        var topic = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));

        // Revalida duplicidade se título/mensagem mudarem
        if ((!topic.getTitle().equals(dto.title()) || !topic.getMessage().equals(dto.message()))
                && repo.existsByTitleAndMessage(dto.title(), dto.message())) {
            throw new IllegalArgumentException("Tópico duplicado (título + mensagem).");
        }

        topic.setTitle(dto.title());
        topic.setMessage(dto.message());
        topic.setAuthor(dto.author());
        topic.setCourse(dto.course());
        var saved = repo.save(topic);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new IllegalArgumentException("Tópico não encontrado");
        repo.deleteById(id);
    }

    private TopicResponse toResponse(Topic t) {
        return new TopicResponse(
                t.getId(), t.getTitle(), t.getMessage(), t.getAuthor(), t.getCourse(), t.getStatus(), t.getCreatedAt()
        );
    }
}

