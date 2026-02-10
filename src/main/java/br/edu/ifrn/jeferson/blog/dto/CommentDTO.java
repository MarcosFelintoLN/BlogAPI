package br.edu.ifrn.jeferson.blog.dto;

public class CommentDTO {
    private Long id;
    private String content;
    private Long postId;
    private Long authorId;

    private Long parentId;

    public CommentDTO() {}

    public CommentDTO(Long id, String content, Long postId, Long authorId, Long parentId) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.authorId = authorId;
        this.parentId = parentId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}
