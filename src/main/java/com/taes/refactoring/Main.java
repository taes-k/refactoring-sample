package com.taes.refactoring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main
{
    public static void main(String[] args)
    {
        Map<String, Play> plays = new HashMap<>();
        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));
        plays.put("othello", new Play("Othello", "tragedy"));

        Invoice invoice = new Invoice();
        invoice.setCustomer("BigCo");
        invoice.setPerformances(
            Arrays.asList(
                new Performance("hamlet", 55)
                , new Performance("as-like", 35)
                , new Performance("othello", 40)));

        Statement statement = new Statement(plays);
        statement.printCharge(invoice);
    }
}
