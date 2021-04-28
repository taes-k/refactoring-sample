package com.taes.refactoring;

public class ComedyPerformanceCaculator extends  PerformanceCaculator
{
    public ComedyPerformanceCaculator(Performance performance, Play play)
    {
        super(performance, play);
    }

    public int getAmount()
    {
        int result;
        result = 30000;
        if (performance.getAudience() > 20)
        {
            result += 10000 + 500 * (performance.getAudience() - 20);
        }
        result += 300 * performance.getAudience();
        return result;
    }

    public int getVolumeCredit()
    {
        // 희극 관객 5명마다 추가 포인트적립
        int additionalCredit = (int) Math.floor(performance.getAudience() / 5);
        return super.getVolumeCredit() + additionalCredit;
    }
}
