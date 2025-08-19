package com.example.demo;
package com.forumhub.api.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest((@NotBlank String username, @NotBlank String password) {}) {}

public record TokenResponse(String token, String type) {}

public record SignupRequest(@NotBlank String username, @NotBlank String password) {}