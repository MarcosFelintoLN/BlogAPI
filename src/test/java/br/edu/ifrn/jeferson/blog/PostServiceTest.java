package br.edu.ifrn.jeferson.blog;

import br.edu.ifrn.jeferson.blog.dto.CreatePostDTO;
import br.edu.ifrn.jeferson.blog.exception.ResourceNotFoundException;
import br.edu.ifrn.jeferson.blog.models.Posts;
import br.edu.ifrn.jeferson.blog.models.Users;
import br.edu.ifrn.jeferson.blog.service.PostService;
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
class PostServiceTest {

    @Mock PostRepository postRepo;
    @Mock UserRepository userRepo;

    @InjectMocks PostService service;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void mockAuthEmail(String email) {
        var auth = new UsernamePasswordAuthenticationToken(email, null, java.util.List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void create_quandoAutenticado_criaPostComAutor() {
        mockAuthEmail("ana@gmail.com");

        Users user = new Users();
        user.setId(10L);
        user.setEmail("ana@gmail.com");

        when(userRepo.findByEmail("ana@gmail.com")).thenReturn(Optional.of(user));

        var dto = new CreatePostDTO();
        dto.setTitle("Titulo");
        dto.setContent("Conteudo");

        ArgumentCaptor<Posts> captor = ArgumentCaptor.forClass(Posts.class);

        var created = service.create(dto);

        verify(postRepo).save(captor.capture());
        Posts saved = captor.getValue();

        assertEquals("Titulo", saved.getTitle());
        assertEquals("Conteudo", saved.getContent());
        assertEquals(user, saved.getAuthor());

        assertEquals("Titulo", created.getTitle());
        assertEquals("Conteudo", created.getContent());
        assertEquals(10L, created.getAuthorId());
    }

    @Test
    void create_quandoNaoAutenticado_lancaIllegalArgumentException() {
        SecurityContextHolder.clearContext();

        CreatePostDTO dto = new CreatePostDTO();
        dto.setTitle("t");
        dto.setContent("c");

        assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        verify(postRepo, never()).save(any());
    }

    @Test
    void findById_quandoNaoExiste_lancaResourceNotFound() {
        when(postRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }
}
