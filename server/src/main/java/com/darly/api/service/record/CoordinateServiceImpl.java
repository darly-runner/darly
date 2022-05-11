package com.darly.api.service.record;

import com.darly.db.entity.record.Coordinate;
import com.darly.db.entity.record.Record;
import com.darly.db.repository.record.CoordinateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("coordinateService")
@RequiredArgsConstructor
public class CoordinateServiceImpl implements CoordinateService {
    private final CoordinateRepository coordinateRepository;

    @Override
    public void createCoordinate(Long recordId, List<String> coordinateLatitudes, List<String> coordinateLongitudes) {
        if (coordinateLatitudes != null && coordinateLongitudes != null)
            coordinateRepository.save(Coordinate.builder()
                    .record(Record.builder().recordId(recordId).build())
                    .coordinateLatitude(String.join(",", coordinateLatitudes))
                    .coordinateLongitude(String.join(",", coordinateLongitudes))
                    .build());
    }

    @Override
    public Coordinate getCoordinate(Long recordId) {
        return coordinateRepository.findByRecord_RecordId(recordId);
    }
}
