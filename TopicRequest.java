package com.forumhub.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TopicRequest(
        @NotBlank @Size(max=200) String title,
        @NotBlank String message,
        @NotBlank String author,
        @NotBlank String course
) {}

public record TopicResponse(
        Long id, String title, String message, String author, String course,
        String status, java.time.LocalDateTime createdAt
) {}


