package tech.developingdeveloper.springloginplayground.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Abhishek Saxena on 01-04-2021.
 */

public interface PostRepository extends JpaRepository<Post, Long> {
}
