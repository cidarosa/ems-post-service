package com.github.cidarosa.algaposts.post.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSummaryOutput {

    private UUID id;
    private String title;
    private String summary;
    private String author;
}
