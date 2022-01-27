package com.example.MyFamilyMoney.models;

public enum AccountType {
    CASH("наличные"),
    CARD("пластиковая карта");

    private final String displayValue;

    private AccountType(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue() {
        return displayValue;
    }
}
