package com.demo.cymelle_backend.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(
        int status,
        Map<String, String> errors
) {
}
