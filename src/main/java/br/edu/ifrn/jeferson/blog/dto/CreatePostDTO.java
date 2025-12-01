package br.edu.ifrn.jeferson.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePostDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long authorId;

    public CreatePostDTO() {}
    // getters e setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}
