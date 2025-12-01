package br.edu.ifrn.jeferson.blog.repository;

import br.edu.ifrn.jeferson.blog.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
