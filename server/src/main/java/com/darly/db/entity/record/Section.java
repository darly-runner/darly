package com.darly.db.entity.record;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tb_section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionId;
    private Long recordId;
    private Float sectionKm;
    private Float sectionPace;
    private Integer sectionCalories;
}
