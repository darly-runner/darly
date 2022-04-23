package com.darly.db.repository.record;

import com.darly.db.entity.record.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
