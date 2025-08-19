package com.forumhub.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleAndMessage(String title, String message);

    // Filtros opcionais
    Page<Topic> findByCourseIgnoreCaseOrderByCreatedAtAsc(String course, Pageable pageable);
    Page<Topic> findByCourseIgnoreCaseAndCreatedAtBetweenOrderByCreatedAtAsc(
            String course, LocalDateTime start, LocalDateTime end, Pageable pageable);
}

