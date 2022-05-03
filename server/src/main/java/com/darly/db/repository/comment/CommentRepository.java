package com.darly.db.repository.comment;

import com.darly.db.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByFeed_FeedId(Long feedId);
}
