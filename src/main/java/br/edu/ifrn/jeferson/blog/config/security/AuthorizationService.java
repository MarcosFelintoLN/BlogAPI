package br.edu.ifrn.jeferson.blog.config.security;

import br.edu.ifrn.jeferson.blog.models.Comments;
import br.edu.ifrn.jeferson.blog.models.Posts;
import br.edu.ifrn.jeferson.blog.models.Users;
import br.edu.ifrn.jeferson.blog.repository.CommentRepository;
import br.edu.ifrn.jeferson.blog.repository.PostRepository;
import br.edu.ifrn.jeferson.blog.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationService {

    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final CommentRepository commentRepo;

    public AuthorizationService(
            UserRepository userRepo,
            PostRepository postRepo,
            CommentRepository commentRepo
    ) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    private Users getUser(Authentication auth) {
        return userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean canEditPost(Long postId, Authentication auth) {
        if (isAdmin(auth)) return true;

        Users user = getUser(auth);
        Posts post = postRepo.findById(postId).orElseThrow();
        return post.getAuthor().getId().equals(user.getId());
    }

    public boolean canEditComment(Long commentId, Authentication auth) {
        if (isAdmin(auth)) return true;

        Users user = getUser(auth);
        Comments comment = commentRepo.findById(commentId).orElseThrow();
        return comment.getAuthor().getId().equals(user.getId());
    }

    public boolean canDeleteComment(Long commentId, Authentication auth) {
        if (isAdmin(auth)) return true;

        Users user = getUser(auth);
        Comments comment = commentRepo.findById(commentId).orElseThrow();

        if (comment.getAuthor().getId().equals(user.getId())) return true;

        return comment.getPost().getAuthor().getId().equals(user.getId());
    }
}
