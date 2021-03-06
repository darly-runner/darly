package com.darly.db.entity.event;

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
@Table(name="tb_event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long eventId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String eventTitle;
    private String eventContent;
    private String eventImage;

    private Long eventDate;


    @Builder
    public Event(Long eventId, User user, String eventTitle, String eventContent,
                 String eventImage, Long eventDate) {
        this.eventId = eventId;
        this.user = user;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImage = eventImage;
        this.eventDate = eventDate;
    }
}
