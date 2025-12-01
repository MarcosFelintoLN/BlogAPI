package br.edu.ifrn.jeferson.blog.dto;

public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private Long authorId;

    public PostDTO() {}
    public PostDTO(Long id, String title, String content, Long authorId) {
        this.id = id; this.title = title; this.content = content; this.authorId = authorId;
    }
    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}
