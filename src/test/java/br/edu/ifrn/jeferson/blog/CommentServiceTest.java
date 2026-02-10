package br.edu.ifrn.jeferson.blog;

import br.edu.ifrn.jeferson.blog.dto.CreateCommentDTO;
import br.edu.ifrn.jeferson.blog.exception.ResourceNotFoundException;
import br.edu.ifrn.jeferson.blog.models.Comments;
import br.edu.ifrn.jeferson.blog.models.Posts;
import br.edu.ifrn.jeferson.blog.models.Users;
import br.edu.ifrn.jeferson.blog.repository.CommentRepository;
import br.edu.ifrn.jeferson.blog.service.CommentService;
import br.edu.ifrn.jeferson.blog.repository.PostRepository;
import br.edu.ifrn.jeferson.blog.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock CommentRepository commentRepo;
    @Mock PostRepository postRepo;
    @Mock UserRepository userRepo;

    @InjectMocks CommentService service;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void mockAuthEmail(String email) {
        var auth = new UsernamePasswordAuthenticationToken(email, null, java.util.List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void create_quandoPostNaoExiste_lancaResourceNotFound() {
        mockAuthEmail("ana@gmail.com");
        when(postRepo.findById(1L)).thenReturn(Optional.empty());

        var dto = new CreateCommentDTO();
        dto.setPostId(1L);
        dto.setContent("oi");
        dto.setParentId(null);

        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
    }

    @Test
    void create_quandoParentDeOutroPost_lancaIllegalArgumentException() {
        mockAuthEmail("ana@gmail.com");

        Users author = new Users();
        author.setId(10L);
        author.setEmail("ana@gmail.com");

        Posts post1 = new Posts();
        post1.setId(1L);

        Posts post2 = new Posts();
        post2.setId(2L);

        Comments parent = new Comments();
        parent.setId(50L);
        parent.setPost(post2);

        when(postRepo.findById(1L)).thenReturn(Optional.of(post1));
        when(userRepo.findByEmail("ana@gmail.com")).thenReturn(Optional.of(author));
        when(commentRepo.findById(50L)).thenReturn(Optional.of(parent));

        CreateCommentDTO dto = new CreateCommentDTO();
        dto.setPostId(1L);
        dto.setContent("resposta");
        dto.setParentId(50L);

        assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        verify(commentRepo, never()).save(any());
    }

}
