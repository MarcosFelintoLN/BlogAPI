package br.edu.ifrn.jeferson.blog.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import br.edu.ifrn.jeferson.blog.models.Users;
import br.edu.ifrn.jeferson.blog.models.Posts;


@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Users author;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Posts getPost() { return post; }
    public void setPost(Posts post) { this.post = post; }

    public Users getAuthor() { return author; }
    public void setAuthor(Users author) { this.author = author; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
