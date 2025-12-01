package br.edu.ifrn.jeferson.blog.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateCommentDTO {
    @NotBlank
    private String content;
    public UpdateCommentDTO() {}
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
