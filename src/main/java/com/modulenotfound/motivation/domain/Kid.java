package com.modulenotfound.motivation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Kid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private int point;

    public void addPoint(int point) {
        this.point += point;
        if (this.point < 0) {
            this.point = 0;
        }
    }

    public void minusPoint(int point) {
        this.point -= point;
        if (this.point < 0) {
            this.point = 0;
        }
    }
}
