package com.darly.db.repository.record;

import com.darly.db.entity.record.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
}
