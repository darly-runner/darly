package com.darly.db.repository.crew;

import com.darly.db.entity.crew.Crew;
import com.darly.db.entity.crew.CrewTitleMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CrewRepository extends JpaRepository<Crew, Long> {
    @Query(value = "select c.crew_id as crewId, c.crew_name as crewName, c.crew_desc as crewDesc, c.crew_image as crewImage, a.address_name as crewAddress, (select count(*) from tb_user_crew where crew_id = c.crew_id) as crewPeopleNum " +
            "from tb_crew c " +
            "left join tb_user_crew uc " +
            "on c.crew_id = uc.crew_id " +
            "and uc.user_id = :userId " +
            "inner join tb_crew_address ca " +
            "on c.crew_id = ca.crew_id " +
            "inner join tb_address a " +
            "on ca.address_id = a.address_id " +
            "where uc.crew_id is null " +
            "and a.address_id = :addressId " +
            "and c.crew_name like %:key% ", nativeQuery = true)
    Page<CrewTitleMapping> findByUserIdAndAddressAndKey(@Param(value = "userId") Long userId, @Param(value = "addressId") Integer address, @Param(value = "key") String key, Pageable pageRequest);

    @Query(value = "select c.crew_id as crewId, c.crew_name as crewName, c.crew_desc as crewDesc, c.crew_image as crewImage, a.address_name as crewAddress, (select count(*) from tb_user_crew where crew_id = c.crew_id) as crewPeopleNum " +
            "from tb_crew c " +
            "left join tb_user_crew uc " +
            "on c.crew_id = uc.crew_id " +
            "and uc.user_id = :userId " +
            "inner join tb_crew_address ca " +
            "on c.crew_id = ca.crew_id " +
            "inner join tb_address a " +
            "on ca.address_id = a.address_id " +
            "where uc.crew_id is null " +
            "and c.crew_name like %:key% ", nativeQuery = true)
    Page<CrewTitleMapping> findByUserIdAndKey(@Param(value = "userId") Long userId, @Param(value = "key") String key, Pageable pageRequest);
}
