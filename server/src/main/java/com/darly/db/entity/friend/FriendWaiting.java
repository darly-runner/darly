package com.darly.db.entity.friend;

import com.darly.db.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "tb_friend_waiting")
public class FriendWaiting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendWaitingId;
    private Long friendOne;
    @ManyToOne
    @JoinColumn(name = "friend_two")
    private User friendTwo;

    @Builder
    public FriendWaiting(Long friendOne, Long friendTwo){
        this.friendOne = friendOne;
        this.friendTwo = User.builder().userId(friendTwo).build();
    }
}
