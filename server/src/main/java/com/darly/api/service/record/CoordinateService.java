package com.darly.api.service.record;

import java.util.List;

public interface CoordinateService {
    void createCoordinate(Long recordId, List<String> coordinateLatitudes, List<String> coordinateLongitudes);
}
