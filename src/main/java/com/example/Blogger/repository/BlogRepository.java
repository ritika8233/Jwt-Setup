package com.example.Blogger.repository;

import com.example.Blogger.model.Author;
import com.example.Blogger.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

//    @Modifying
//    @Query("delete from Blog b where b.author.email=:email")
    List<Blog> removeByAuthor(Author email);

}
