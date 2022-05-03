package com.darly.db.entity.feed;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "tb_feed_image")
public class FeedImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedImageId;
    @ManyToOne
    @JoinColumn(name = "feed_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Feed feed;
    private String feedImage;
}
