package com.taes.refactoring;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Statement
{
    public void printCharge(Invoice invoice, Map<String, Play> plays)
    {
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder();
        result.append(String.format("청구 내역 (고객명 : %s)\n", invoice.getCustomer()));
        NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
        nf.setMaximumFractionDigits(2);

        for(Performance perf : invoice.getPerformances())
        {
            Play play = plays.get(perf.getPlayId());
            int thisAmount = 0;

            switch (play.getType())
            {
                case "tragedy": //비극
                    thisAmount = 40000;
                    if (perf.getAudience() > 30)
                    {
                        thisAmount += 1000 * (perf.getAudience() - 30);
                    }
                    break;
                case "comedy": //희극
                    thisAmount = 30000;
                    if (perf.getAudience() > 20)
                    {
                        thisAmount += 10000 + 500 * (perf.getAudience() - 20);
                    }
                    thisAmount += 300 * perf.getAudience();
                    break;
                default:
                    String errorMessage = String.format("알수없는 장르 : %s", play.getType());
                    throw new RuntimeException(errorMessage);
            }

            // 포인트적립
            volumeCredits += Math.max(perf.getAudience() - 30, 0);

            // 희극 관객 5명마다 추가 포인트적립
            if (play.getType().equals("comedy"))
            {
                volumeCredits += Math.floor(perf.getAudience() / 5);
            }

            // 청구 내역 출력
            result.append(String.format("%s: %d (%d석)\n", play.getName(), thisAmount, perf.getAudience()));
            totalAmount += thisAmount;
        }

        result.append(String.format("총액 : %s\n", nf.format(totalAmount/100)));
        result.append(String.format("적립 포인트 : %d점\n", volumeCredits));

        System.out.println(result);
    }
}
