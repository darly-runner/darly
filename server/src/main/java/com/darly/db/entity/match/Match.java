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
@Table(name = "tb_match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    private Long crewId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User host;
    private String matchTitle;
    private Short matchMaxPerson;
    private Short matchGoalDistance;
    private Long matchDate;
    private Long matchStartTime;
    private Character matchStatus;
}
