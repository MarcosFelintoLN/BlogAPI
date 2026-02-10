package br.edu.ifrn.jeferson.blog.repository;

import br.edu.ifrn.jeferson.blog.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByPostId(Long postId);
}
