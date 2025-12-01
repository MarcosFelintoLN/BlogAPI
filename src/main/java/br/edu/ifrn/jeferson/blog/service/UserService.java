package br.edu.ifrn.jeferson.blog.service;

import br.edu.ifrn.jeferson.blog.dto.*;
import br.edu.ifrn.jeferson.blog.exception.ResourceNotFoundException;
import br.edu.ifrn.jeferson.blog.models.Users;
import br.edu.ifrn.jeferson.blog.models.Role;
import br.edu.ifrn.jeferson.blog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserDTO create(CreateUserDTO dto) {
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // opcional: encriptar senha
        user.setRole(Role.USER);

        repo.save(user);
        return toDTO(user);
    }

    public List<UserDTO> findAll() {
        return repo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public UserDTO update(Long id, UpdateUserDTO dto) {
        Users user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        }

        repo.save(user);
        return toDTO(user);
    }

    public void delete(Long id) {
        Users user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        repo.delete(user);
    }

    private UserDTO toDTO(Users user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
