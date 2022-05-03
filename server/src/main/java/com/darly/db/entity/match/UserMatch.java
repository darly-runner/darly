package com.darly.db.entity.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Builder
@Table(name = "tb_user_match")
public class UserMatch {
    @EmbeddedId
    private UserMatchId userMatchId;
    private Character userMatchStatus;
}
