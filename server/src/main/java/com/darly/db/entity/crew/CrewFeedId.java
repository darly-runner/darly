package com.darly.db.entity.crew;

import com.darly.db.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CrewFeedId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;
    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Builder
    public CrewFeedId(Long crewId, Long feedId) {
        this.crew = Crew.builder().crewId(crewId).build();
        this.feed = Feed.builder().userId(feedId).build();
    }
}
