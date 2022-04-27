package com.darly.db.entity.match;

import com.darly.db.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "tb_match_result")
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchResultId;
    private Long matchId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Short matchResultRank;
    private Integer matchResultTime;
    private Float matchResultPace;
}
