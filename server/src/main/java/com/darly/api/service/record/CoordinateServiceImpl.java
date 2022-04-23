package com.darly.api.service.record;

import com.darly.db.entity.record.Coordinate;
import com.darly.db.repository.record.CoordinateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service("coordinateService")
@RequiredArgsConstructor
public class CoordinateServiceImpl implements CoordinateService {
    private final CoordinateRepository coordinateRepository;

    @Override
    public void createCoordinate(Long recordId, List<String> coordinateLatitudes, List<String> coordinateLongitudes) {
        StringBuilder latitude = new StringBuilder();
        StringBuilder longitude = new StringBuilder();
        System.out.println(coordinateLatitudes.toString());
        System.out.println(coordinateLongitudes.toString());
        coordinateRepository.save(Coordinate.builder()
                .recordId(recordId)
                .coordinateLatitude(String.join(",", coordinateLatitudes))
                .coordinateLongitude(String.join(",", coordinateLongitudes))
                .build());
    }
}