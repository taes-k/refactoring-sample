package com.taes.refactoring;

public abstract class PerformanceCaculator
{
    protected Performance performance;
    protected Play play;

    protected PerformanceCaculator(Performance performance, Play play)
    {
        this.performance = performance;
        this.play = play;
    }

    public static PerformanceCaculator createPerformanceCalculator(Performance performance, Play play)
    {
        switch (play.getType())
        {
            case "tragedy" : return new TragedyPerformanceCaculator(performance, play);
            case "comedy" : return new ComedyPerformanceCaculator(performance, play);
            default :
                String errorMessage = String.format("알수없는 장르 : %s", play.getType());
                throw new RuntimeException(errorMessage);
        }
    }

    public abstract int getAmount();

    public int getVolumeCredit()
    {
        return Math.max(this.performance.getAudience() - 30 , 0);
    }
}
