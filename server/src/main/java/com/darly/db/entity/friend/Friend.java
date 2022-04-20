package com.darly.db.entity.friend;

import com.darly.db.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "tb_friend")
public class Friend {
    @Id
    private Long friendId;
    private Long friendOne;
    @ManyToOne
    @JoinColumn(name = "friend_two")
    private User friendTwo;
}
