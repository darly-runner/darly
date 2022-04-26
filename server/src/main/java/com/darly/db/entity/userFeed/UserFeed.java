package com.darly.db.entity.userFeed;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_user_feed")
public class UserFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userFeedId;
    private Long userId;
    private String userFeedImage;

    @Builder
    public UserFeed(Long userFeedId, Long userId, String userFeedImage) {
        this.userFeedId = userFeedId;
        this.userId = userId;
        this.userFeedImage = userFeedImage;
    }
}
