package tech.developingdeveloper.springloginplayground.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tech.developingdeveloper.springloginplayground.appuser.AppUserService;
import tech.developingdeveloper.springloginplayground.entity.Post;
import tech.developingdeveloper.springloginplayground.entity.PostRepository;

import java.util.List;

/**
 * Created by Abhishek Saxena on 01-04-2021.
 */

@RestController
@RequestMapping("someData")
public class SomeResource {

    private final AppUserService appUserService;
    private final PostRepository postRepository;

    public SomeResource(AppUserService appUserService, PostRepository postRepository) {
        this.appUserService = appUserService;
        this.postRepository = postRepository;
    }

    @GetMapping
    public String helloWorld() {
        return "hello world";
    }


    @GetMapping("{username}")
    public UserDetails getUserByUsername(@PathVariable String username) {
        return appUserService.loadUserByUsername(username);
    }

    @PostMapping
    public ResponseEntity<Post> createPost() {
        Post post = new Post();
        post.setPostId(1L);
        post.setAppUser(appUserService.loadUserByUsername("test@test.com"));
        postRepository.save(post);
        return ResponseEntity.ok(post);
    }

    @GetMapping("posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postRepository.findAll());
    }
}
