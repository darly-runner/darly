package com.darly.api.service.userFeed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserFeedService {
    Page<String> getUserFeedList(Long friendId, Pageable page);
}
