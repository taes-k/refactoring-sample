package com.taes.refactoring;

import lombok.Getter;
import lombok.Setter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Statement
{
    private final Map<String, Play> plays;

    public Statement(Map<String, Play> plays)
    {
        this.plays = plays;
    }

    public void printPlainText(Invoice invoice)
    {
        Data data = createData(invoice);
        System.out.println(renderPlainText(data));
    }

    public void printHtmlText(Invoice invoice)
    {
        Data data = createData(invoice);
        System.out.println(renderHtmlText(data));
    }

    private Data createData(Invoice invoice)
    {
        Data result = new Data();

        result.setCustomer(invoice.getCustomer());
        result.setPerformances(invoice.getPerformances());
        result.setTotalAmount(getTotalAmount(invoice));
        result.setTotalVolumeCredits(getTotalVolumeCredits(invoice));

        return result;
    }

    private String renderPlainText(Data data)
    {
        StringBuilder result = new StringBuilder();
        result.append(String.format("청구 내역 (고객명 : %s)\n", data.getCustomer()));

        for(Performance perf :data.getPerformances())
        {
            // 청구 내역 출력
            result.append(String.format("%s: %d (%d석)\n", playFor(perf).getName(), amountFor(perf), perf.getAudience()));
        }

        result.append(String.format("총액 : %s\n", toUSD(data.getTotalAmount())));
        result.append(String.format("적립 포인트 : %d점\n", data.getTotalVolumeCredits()));

        return result.toString();
    }

    private String renderHtmlText(Data data)
    {
        StringBuilder result = new StringBuilder();
        result.append(String.format("<h1>청구 내역 (고객명 : %s)</h1>\n", data.getCustomer()));
        result.append("<table>");
        result.append("<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>");

        for(Performance perf :data.getPerformances())
        {
            // 청구 내역 출력
            result.append(String.format("<tr><td>%s</td><td>%d</td><td>%d석</td></tr>\n"
                , playFor(perf).getName(), perf.getAudience(), amountFor(perf)));
        }
        result.append("</table>");

        result.append(String.format("<p>총액 : <em>%s</em></p>\n", toUSD(data.getTotalAmount())));
        result.append(String.format("<p>적립 포인트 : <em>%d</em>점</p>\n", data.getTotalVolumeCredits()));

        return result.toString();
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

    private int volumeCreditsFor(Performance performance)
    {
        return PerformanceCaculator.createPerformanceCalculator(performance, playFor(performance)).getVolumeCredit();
    }

    private int amountFor(Performance performance)
    {
        return PerformanceCaculator.createPerformanceCalculator(performance, playFor(performance)).getAmount();
    }

    private Play playFor(Performance performance)
    {
        return plays.get(performance.getPlayId());
    }

    @Getter
    @Setter
    private class Data
    {
        private String customer;
        private List<Performance> performances;
        private int totalAmount;
        private int totalVolumeCredits;
    }
}
