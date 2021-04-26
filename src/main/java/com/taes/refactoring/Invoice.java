package com.taes.refactoring;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Invoice
{
    private String customer;
    private List<Performance> performances;
}
