package com.github.cidarosa.algaposts.post.service.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostInput {

    @NotBlank(message = "O título do post é obrigatório")
    @Size(min = 3, message = "O título deve ter pelo menos 3 caracteres")
    private String title;

    @NotBlank(message = "O conteúdo do post é obrigatório")
    @Size(min = 3, message = "O conteúdo do post deve ter pelo menos 3 caracteres")
    private String body;

    @NotBlank(message = "O autor do post é obrigatório")
    @Size(min = 3, message = "O autor do post deve ter pelo menos 3 caracteres")
    private String author;
}
