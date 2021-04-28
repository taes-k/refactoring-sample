package com.taes.refactoring;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Statement
{
    private final Map<String, Play> plays;

    public Statement(Map<String, Play> plays)
    {
        this.plays = plays;
    }

    public void printCharge(Invoice invoice)
    {
        StringBuilder result = new StringBuilder();
        result.append(String.format("청구 내역 (고객명 : %s)\n", invoice.getCustomer()));

        for(Performance perf : invoice.getPerformances())
        {
            // 청구 내역 출력
            result.append(String.format("%s: %d (%d석)\n", playFor(perf).getName(), amountFor(perf), perf.getAudience()));
        }

        result.append(String.format("총액 : %s\n", toUSD(getTotalAmount(invoice))));
        result.append(String.format("적립 포인트 : %d점\n", getTotalVolumeCredits(invoice)));

        System.out.println(result);
    }

    private int getTotalAmount(Invoice invoice)
    {
        int totalAmount = 0;
        for(Performance perf : invoice.getPerformances())
        {
            totalAmount += amountFor(perf);
        }
        return totalAmount;
    }

    private int getTotalVolumeCredits(Invoice invoice)
    {
        int volumeCredits = 0;
        for(Performance perf : invoice.getPerformances())
        {
            volumeCredits += volumeCreditsFor(perf);
        }
        return volumeCredits;
    }

    private String toUSD(int number)
    {
        NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
        nf.setMaximumFractionDigits(2);
        return nf.format(number/100);
    }

    private int volumeCreditsFor(Performance perf)
    {
        int result = 0;
        result += Math.max(perf.getAudience() - 30, 0);

        // 희극 관객 5명마다 추가 포인트적립
        if (playFor(perf).getType().equals("comedy"))
        {
            result += Math.floor(perf.getAudience() / 5);
        }

        return result;
    }

    private int amountFor(Performance performance)
    {
        int result;
        switch (playFor(performance).getType())
        {
            case "tragedy": //비극
                result = 40000;
                if (performance.getAudience() > 30)
                {
                    result += 1000 * (performance.getAudience() - 30);
                }
                break;
            case "comedy": //희극
                result = 30000;
                if (performance.getAudience() > 20)
                {
                    result += 10000 + 500 * (performance.getAudience() - 20);
                }
                result += 300 * performance.getAudience();
                break;
            default:
                String errorMessage = String.format("알수없는 장르 : %s", playFor(performance).getType());
                throw new RuntimeException(errorMessage);
        }
        return result;
    }

    private Play playFor(Performance performance)
    {
        return plays.get(performance.getPlayId());
    }
}
