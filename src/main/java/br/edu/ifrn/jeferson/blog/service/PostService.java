package br.edu.ifrn.jeferson.blog.service;

import br.edu.ifrn.jeferson.blog.dto.*;
import br.edu.ifrn.jeferson.blog.exception.ResourceNotFoundException;
import br.edu.ifrn.jeferson.blog.models.Posts;
import br.edu.ifrn.jeferson.blog.models.Users;
import br.edu.ifrn.jeferson.blog.repository.PostRepository;
import br.edu.ifrn.jeferson.blog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public PostService(PostRepository postRepo, UserRepository userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public PostDTO create(CreatePostDTO dto) {
        Users author = userRepo.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + dto.getAuthorId()));
        Posts p = new Posts();
        p.setTitle(dto.getTitle());
        p.setContent(dto.getContent());
        p.setAuthor(author);
        postRepo.save(p);
        return toDTO(p);
    }

    public List<PostDTO> findAll() {
        return postRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PostDTO findById(Long id) {
        return postRepo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
    }

    public PostDTO update(Long id, UpdatePostDTO dto) {
        Posts p = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
        p.setTitle(dto.getTitle());
        p.setContent(dto.getContent());
        postRepo.save(p);
        return toDTO(p);
    }

    public void delete(Long id) {
        Posts p = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
        postRepo.delete(p);
    }

    private PostDTO toDTO(Posts p) {
        return new PostDTO(p.getId(), p.getTitle(), p.getContent(), p.getAuthor().getId());
    }
}
