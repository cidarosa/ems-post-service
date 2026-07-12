package com.github.cidarosa.algaposts.post.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostProcessingResult {

    private UUID postId;
    private Integer wordCount;
    private BigDecimal calculatedValue;
}

