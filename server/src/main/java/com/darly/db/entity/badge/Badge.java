package com.darly.db.entity.badge;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="tb_badge")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BADGE_ID")
    private Long badgeId;
    private String badgeName;
    private String badgeImage;
    private String badgeCondition;

    @Builder
    public Badge(Long badgeId, String badgeName, String badgeImage, String badgeCondition) {
        this.badgeId = badgeId;
        this.badgeName = badgeName;
        this.badgeImage = badgeImage;
        this.badgeCondition = badgeCondition;
    }
}
