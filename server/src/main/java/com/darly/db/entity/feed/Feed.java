package com.darly.db.entity.feed;

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
@Table(name = "tb_feed")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;
    @ManyToOne
    @JoinColumn(name = "host_id")
    private User user;
    private String feedTitle;
    private String feedContent;
    private Long feedLike;
    private Long feedDate;
}
