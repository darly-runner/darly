package com.darly.api.service.record;

import com.darly.db.entity.record.Coordinate;

import java.util.List;

public interface CoordinateService {
    void createCoordinate(Long recordId, List<String> coordinateLatitudes, List<String> coordinateLongitudes);
    Coordinate getCoordinate(Long recordId);
}
