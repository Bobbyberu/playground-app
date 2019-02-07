package com.playground.model.dto;

import com.playground.model.entity.Sport;
import lombok.Getter;

@Getter
public class SportDto {

    private int id;

    private String name;

    private String symbol;

    public SportDto(Sport sport) {
        this.id = sport.getId();
        this.name = sport.getName();
        this.symbol = sport.getSymbol();
    }
}
