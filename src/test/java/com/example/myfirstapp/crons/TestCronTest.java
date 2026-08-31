package com.example.myfirstapp.crons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TestCronTest {

    private final TestCron testCron = new TestCron();

    @Test
    void shouldRunDemoCron() {
        assertDoesNotThrow(() -> testCron.demoCron());
    }

    @Test
    void shouldRunDemoCron2() {
        assertDoesNotThrow(() -> testCron.demoCron2());
    }
}