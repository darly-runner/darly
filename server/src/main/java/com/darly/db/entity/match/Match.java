package com.darly.db.entity.match;

import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "tb_match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;
    @ManyToOne
    @JoinColumn(name = "host_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User host;
    private String matchTitle;
    private Short matchMaxPerson;
    private Short matchCurPerson;
    private Float matchGoalDistance;
    private Long matchDate;
    private Long matchStartTime;
    private Character matchStatus;
}
