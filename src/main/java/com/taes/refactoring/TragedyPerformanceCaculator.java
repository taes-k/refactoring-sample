package com.taes.refactoring;

public class TragedyPerformanceCaculator extends  PerformanceCaculator
{
    public TragedyPerformanceCaculator(Performance performance, Play play)
    {
        super(performance, play);
    }

    public int getAmount()
    {
        int result;
        result = 40000;
        if (performance.getAudience() > 30)
        {
            result += 1000 * (performance.getAudience() - 30);
        }
        return result;
    }

    public int getVolumeCredit()
    {
        return super.getVolumeCredit();
    }
}
