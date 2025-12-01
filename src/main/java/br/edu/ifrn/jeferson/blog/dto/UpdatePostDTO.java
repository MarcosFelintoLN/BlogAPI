package br.edu.ifrn.jeferson.blog.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdatePostDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public UpdatePostDTO() {}
    // getters e setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}