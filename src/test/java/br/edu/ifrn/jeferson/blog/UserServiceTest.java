package br.edu.ifrn.jeferson.blog;

import br.edu.ifrn.jeferson.blog.dto.CreateUserDTO;
import br.edu.ifrn.jeferson.blog.exception.ResourceNotFoundException;
import br.edu.ifrn.jeferson.blog.models.Role;
import br.edu.ifrn.jeferson.blog.models.Users;
import br.edu.ifrn.jeferson.blog.service.UserService;
import br.edu.ifrn.jeferson.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository repo;
    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks UserService service;

    @Test
    void create_quandoEmailNaoExiste_criaUsuarioComSenhaCriptografadaERoleUSER() {
        var dto = new CreateUserDTO();
        dto.setName("Marcos");
        dto.setEmail("marcos@mail.com");
        dto.setPassword("123");;

        when(repo.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("hashed");

        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);

        var created = service.create(dto);

        verify(repo).save(captor.capture());
        Users saved = captor.getValue();

        assertEquals("Marcos", saved.getName());
        assertEquals("marcos@mail.com", saved.getEmail());
        assertEquals("hashed", saved.getPassword());
        assertEquals(Role.USER, saved.getRole());

        assertEquals("Marcos", created.getName());
        assertEquals("marcos@mail.com", created.getEmail());
        assertEquals("USER", created.getRole());
    }

    @Test
    void create_quandoEmailJaExiste_lancaIllegalArgumentException() {
        var dto = new CreateUserDTO();
        dto.setName("Marcos");
        dto.setEmail("marcos@mail.com");
        dto.setPassword("123");;
        when(repo.findByEmail(dto.getEmail())).thenReturn(Optional.of(new Users()));

        assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        verify(repo, never()).save(any());
    }

    @Test
    void findById_quandoNaoExiste_lancaResourceNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }
}
