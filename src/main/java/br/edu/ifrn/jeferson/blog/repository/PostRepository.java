package br.edu.ifrn.jeferson.blog.repository;

import br.edu.ifrn.jeferson.blog.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Long> {
}
