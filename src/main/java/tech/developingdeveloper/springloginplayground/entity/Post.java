package tech.developingdeveloper.springloginplayground.entity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import tech.developingdeveloper.springloginplayground.appuser.AppUser;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Abhishek Saxena on 01-04-2021.
 */

@Entity
public class Post {

    @Id
    private Long postId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email")
    private AppUser appUser;

    public Post() {
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public UserDetails getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postId, post.postId) && Objects.equals(appUser, post.appUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, appUser);
    }
}
