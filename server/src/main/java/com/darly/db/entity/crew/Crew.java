package com.darly.db.entity.crew;

import com.darly.db.entity.User;
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
@Table(name = "tb_crew")
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crewId;
    @ManyToOne
    @JoinColumn(name = "host_id")
    private User user;
    private String crewName;
    private String crewDesc;
    private String crewNotice;
    private Character crewJoin;
}
