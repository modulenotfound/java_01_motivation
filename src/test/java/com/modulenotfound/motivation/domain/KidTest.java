package com.modulenotfound.motivation.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KidTest {

    private Kid kid;

    @Before
    public void setUp() throws Exception {
        this.kid = Kid.builder()
                    .id(1)
                    .name("kid")
                    .point(10)
                    .build();
    }

    @Test
    public void addPoint() {
        this.kid.addPoint(20);
        assertThat(this.kid.getPoint()).isEqualTo(30);
    }

    @Test
    public void addPoint_shouldReturn0() {
        this.kid.addPoint(-20);
        assertThat(this.kid.getPoint()).isEqualTo(0);
    }

    @Test
    public void minusPoint() {
        this.kid.minusPoint(5);
        assertThat(this.kid.getPoint()).isEqualTo(5);
    }

    @Test
    public void minusPoint_shouldReturn0() {
        this.kid.minusPoint(20);
        assertThat(this.kid.getPoint()).isEqualTo(0);
    }
}