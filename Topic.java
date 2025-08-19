package com.forumhub.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "topics",
        uniqueConstraints = @UniqueConstraint(name="uk_topics_title_message", columnNames={"title","message"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=200)
    private String title;

    @Column(nullable=false, columnDefinition="TEXT")
    private String message;

    @Column(nullable=false, length=120)
    private String course;

    @Column(nullable=false, length=120)
    private String author;

    @Column(nullable=false, length=30)
    private String status; // e.g. OPEN, CLOSED

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;
}

}
