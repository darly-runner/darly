package com.darly.db.entity.comment;

import com.darly.db.entity.feed.Feed;
import com.darly.db.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "tb_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @ManyToOne
    @JoinColumn(name = "host_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;
    private String commentContent;
    private Long commentDate;
}
