package com.darly.db.entity.friend;

import com.darly.db.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "tb_friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;
    private Long friendOne;
    @ManyToOne
    @JoinColumn(name = "friend_two")
    private User friendTwo;

    @Builder
    public Friend(Long friendOne, Long friendTwo){
        this.friendOne = friendOne;
        this.friendTwo = User.builder().userId(friendTwo).build();
    }
}
