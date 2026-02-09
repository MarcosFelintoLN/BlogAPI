package br.edu.ifrn.jeferson.blog.service;

import br.edu.ifrn.jeferson.blog.dto.*;
import br.edu.ifrn.jeferson.blog.exception.ResourceNotFoundException;
import br.edu.ifrn.jeferson.blog.models.Comments;
import br.edu.ifrn.jeferson.blog.models.Posts;
import br.edu.ifrn.jeferson.blog.models.Users;
import br.edu.ifrn.jeferson.blog.repository.CommentRepository;
import br.edu.ifrn.jeferson.blog.repository.PostRepository;
import br.edu.ifrn.jeferson.blog.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public CommentService(CommentRepository commentRepo, PostRepository postRepo, UserRepository userRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public CommentDTO create(CreateCommentDTO dto) {
        Posts post = postRepo.findById(dto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + dto.getPostId()));

        Users author = getAuthenticatedUser();

        Comments parent = null;
        if (dto.getParentId() != null) {
            parent = commentRepo.findById(dto.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found: " + dto.getParentId()));

            if (!parent.getPost().getId().equals(post.getId())) {
                throw new IllegalArgumentException("Parent comment must belong to the same post");
            }
        }

        Comments c = new Comments();
        c.setContent(dto.getContent());
        c.setPost(post);
        c.setAuthor(author);
        c.setParent(parent);

        commentRepo.save(c);
        return toDTO(c);
    }

    public List<CommentDTO> findAll() {
        return commentRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> findByPostId(Long postId) {
        return commentRepo.findByPostId(postId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CommentDTO findById(Long id) {
        return commentRepo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));
    }

    public CommentDTO update(Long id, UpdateCommentDTO dto) {
        Comments c = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));

        c.setContent(dto.getContent());
        commentRepo.save(c);
        return toDTO(c);
    }

    public void delete(Long id) {
        Comments c = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));
        commentRepo.delete(c);
    }

    private Users getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        String email = auth.getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by email: " + email));
    }

    private CommentDTO toDTO(Comments c) {
        Long parentId = (c.getParent() != null) ? c.getParent().getId() : null;
        return new CommentDTO(c.getId(), c.getContent(), c.getPost().getId(), c.getAuthor().getId(), parentId);
    }
}
