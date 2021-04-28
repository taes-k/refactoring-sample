package com.taes.refactoring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PerformanceCaculatorTest
{
    @Test
    void getPlayAmount_success()
    {
        // given
        Play play = new Play("Hamlet", "tragedy");
        Performance performance = new Performance("hamlet", 55);

        PerformanceCaculator performanceCaculator = PerformanceCaculator.createPerformanceCalculator(performance, play);

        // when
        int amount = performanceCaculator.getAmount();

        // then
        Assertions.assertEquals(65000, amount);
    }

    @Test
    void getPlayVolumeCredit_success()
    {
        // given
        Play play = new Play("Hamlet", "tragedy");
        Performance performance = new Performance("hamlet", 55);

        PerformanceCaculator performanceCaculator = PerformanceCaculator.createPerformanceCalculator(performance, play);

        // when
        int amount = performanceCaculator.getVolumeCredit();

        // then
        Assertions.assertEquals(25, amount);
    }
}
