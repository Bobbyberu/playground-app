package com.playground.repository;

import com.playground.model.Playground;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaygroundRepository extends CrudRepository<Playground, Integer> {

    @Query("SELECT distinct pl FROM Playground pl " +
            "LEFT JOIN pl.sports ps " +
            "WHERE lower(ps.name) LIKE lower(concat('%',?1,'%')) " +
            "OR lower(pl.city) LIKE lower(concat('%',?1,'%')) " +
            "OR lower(pl.address) LIKE lower(concat('%',?1,'%')) " +
            "OR lower(pl.name) LIKE lower(concat('%',?1,'%')) ")
    List<Playground> search(String keywords);

}
