package com.darly.db.entity.userFeed;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "tb_user_feed")
public class UserFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userFeedId;
    private Long userId;
    private String userFeedImage;
}
