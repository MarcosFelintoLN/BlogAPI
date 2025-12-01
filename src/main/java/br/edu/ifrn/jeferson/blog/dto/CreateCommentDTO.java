package br.edu.ifrn.jeferson.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCommentDTO {
    @NotBlank
    private String content;

    @NotNull
    private Long postId;

    @NotNull
    private Long authorId;

    public CreateCommentDTO() {}
    // getters e setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}
