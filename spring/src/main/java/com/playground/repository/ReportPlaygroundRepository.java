package com.playground.repository;

import com.playground.model.entity.Playground;
import com.playground.model.entity.ReportPlayground;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPlaygroundRepository extends CrudRepository<ReportPlayground, Integer> {

    @Query("SELECT rp FROM ReportPlayground rp WHERE rp.playground= ?1")
    List<ReportPlayground> getByPlayground(Playground playground);

    @Query("SELECT rp FROM ReportPlayground rp WHERE rp.playground= ?1 AND rp.id = ?2")
    ReportPlayground getOneByPlayground(Playground playground, int reportPlaygroundId);
}